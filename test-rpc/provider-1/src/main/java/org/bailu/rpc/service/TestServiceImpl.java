package org.bailu.rpc.service;

import org.bailu.rpc.TestService;
import org.bailu.rpc.annotation.RpcService;

@RpcService
public class TestServiceImpl implements TestService {

    @Override
    public void test(String key) {
        System.out.println(1/0);
        System.out.println("服务提供1 test 测试成功  :" + key);
    }

    @Override
    public void test2(String key) {
        System.out.println("服务提供1 test2 测试成功  :" + key);
    }


}
