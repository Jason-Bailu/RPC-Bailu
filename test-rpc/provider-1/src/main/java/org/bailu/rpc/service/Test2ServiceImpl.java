package org.bailu.rpc.service;

import org.bailu.rpc.Test2Service;
import org.bailu.rpc.annotation.RpcService;

@RpcService
public class Test2ServiceImpl implements Test2Service {
    @Override
    public String test(String key) {
        System.out.println("服务提供1 test2 测试成功 :" + key);
        return key;
    }
}
