package org.bailu.rpc.config;

import org.bailu.rpc.annotation.PropertiesField;
import org.bailu.rpc.annotation.PropertiesPrefix;
import org.bailu.rpc.constant.RegistryRules;
import org.bailu.rpc.constant.SerializationRules;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@PropertiesPrefix("rpc")
public class RpcProperties {
    // netty端口
    @PropertiesField
    private Integer port;
    // 注册中心地址
    @PropertiesField
    private String registerAddr;
    // 注册中心类型
    @PropertiesField
    private String registerType = RegistryRules.ZOOKEEPER;
    // 注册中心密码
    @PropertiesField
    private String registerPsw;
    // 序列化
    @PropertiesField
    private String serialization = SerializationRules.JSON;
    // 额外配置
    @PropertiesField("service")
    private Map<String, Object> serviceAttachments = new HashMap<>();
    @PropertiesField("client")
    private Map<String, Object> clientAttachments = new HashMap<>();
    static RpcProperties rpcProperties;

    public static RpcProperties getInstance(){
        if (rpcProperties == null) {
            rpcProperties = new RpcProperties();
        }
        return rpcProperties;
    }

    private RpcProperties(){}

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getRegisterAddr() {
        return registerAddr;
    }

    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        if(registerType == null || registerType.equals("")){
            registerType = RegistryRules.ZOOKEEPER;
        }
        this.registerType = registerType;
    }

    public String getRegisterPsw() {
        return registerPsw;
    }

    public void setRegisterPsw(String registerPsw) {
        this.registerPsw = registerPsw;
    }

    public String getSerialization() {
        return serialization;
    }

    public void setSerialization(String serialization) {
        if(serialization == null || serialization.equals("")){
            serialization = SerializationRules.JSON;
        }
        this.serialization = serialization;
    }

    public Map<String, Object> getServiceAttachments() {
        return serviceAttachments;
    }

    public void setServiceAttachments(Map<String, Object> serviceAttachments) {
        this.serviceAttachments = serviceAttachments;
    }

    public Map<String, Object> getClientAttachments() {
        return clientAttachments;
    }

    public void setClientAttachments(Map<String, Object> clientAttachments) {
        this.clientAttachments = clientAttachments;
    }

    public static RpcProperties getRpcProperties() {
        return rpcProperties;
    }

    public static void setRpcProperties(RpcProperties rpcProperties) {
        RpcProperties.rpcProperties = rpcProperties;
    }

    // 能够解析任意对象属性的工具类
    public static void init(Environment environment) {}
}
