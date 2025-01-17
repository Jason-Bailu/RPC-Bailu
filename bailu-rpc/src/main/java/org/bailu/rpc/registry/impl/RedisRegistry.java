package org.bailu.rpc.registry.impl;

import com.alibaba.fastjson.JSON;
import org.bailu.rpc.common.RpcServiceNameBuilder;
import org.bailu.rpc.common.ServiceMeta;
import org.bailu.rpc.config.RpcProperties;
import org.bailu.rpc.registry.RegistryService;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RedisRegistry implements RegistryService {
    private JedisPool jedisPool;
    private String UUID;
    private static final int ttl = 10 * 1000;
    private Set<String> serviceMap = new HashSet<>();
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public RedisRegistry() {
        RpcProperties properties = RpcProperties.getInstance();
        String[] split = properties.getRegisterAddr().split(":");
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        jedisPool = new JedisPool(poolConfig, split[0], Integer.valueOf(split[1]));
        this.UUID = java.util.UUID.randomUUID().toString();
        heartbeat();
    }

    private Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        RpcProperties properties = RpcProperties.getInstance();
        if(!ObjectUtils.isEmpty(properties.getRegisterPsw())){
            jedis.auth(properties.getRegisterPsw());
        }
        return jedis;
    }

    private void heartbeat() {
        int sch = 5;
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
           for (String key : serviceMap) {
               List<ServiceMeta> serviceNodes = listServices(key);
               Iterator<ServiceMeta> iterator =serviceNodes.iterator();
               while (iterator.hasNext()) {
                   ServiceMeta node = iterator.next();
                   if (node.getEndTime() < new Date().getTime()) {
                       iterator.remove();
                   }
                   if (node.getUUID().equals(this.UUID)) {
                       node.setEndTime(node.getEndTime() + ttl/2);
                   }
               }
               if (!ObjectUtils.isEmpty(serviceNodes)) {
                   loadService(key, serviceNodes);
               }
           }
        }, sch, sch, TimeUnit.SECONDS);
    }

    private void loadService(String key, List<ServiceMeta> serviceMetas) {
        String script = "redis.call('DEL', KEYS[1])\n" +
                "for i = 1, #ARGV do\n" +
                "   redis.call('RPUSH', KEYS[1], ARGV[i])\n" +
                "end \n"+
                "redis.call('EXPIRE', KEYS[1],KEYS[2])";
        List<String> keys = new ArrayList<>();
        keys.add(key);
        keys.add(String.valueOf(10));
        List<String> values = serviceMetas.stream().map(o -> JSON.toJSONString(o)).collect(Collectors.toList());
        Jedis jedis = getJedis();
        jedis.eval(script, keys, values);
        jedis.close();
    }

    private List<ServiceMeta> listServices(String key) {
        Jedis jedis = getJedis();
        List<String> list = jedis.lrange(key, 0, -1);
        jedis.close();
        List<ServiceMeta> serviceMetas = list.stream().map(o -> JSON.parseObject(o, ServiceMeta.class)).collect(Collectors.toList());
        return serviceMetas;
    }

    @Override
    public void register(ServiceMeta serviceMeta) throws Exception {
        String key = RpcServiceNameBuilder.buildServiceKey(serviceMeta.getServiceName(), serviceMeta.getServiceVersion());
        if (!serviceMap.contains(key)) {
            serviceMap.add(key);
        }
        serviceMeta.setUUID(this.UUID);
        serviceMeta.setEndTime(new Date().getTime() + ttl);
        Jedis jedis = getJedis();
        String script = "redis.call('RPUSH', KEYS[1], ARGV[1])\n" +
                "redis.call('EXPIRE', KEYS[1], ARGV[2])";
        List<String> value = new ArrayList<>();
        value.add(JSON.toJSONString(serviceMeta));
        value.add(String.valueOf(10));
        jedis.eval(script, Collections.singletonList(key), value);
        jedis.close();
    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) throws Exception {

    }

    @Override
    public List<ServiceMeta> discoveries(String serviceName) {
        return listServices(serviceName);
    }

    @Override
    public void destroy() throws IOException {

    }
}
