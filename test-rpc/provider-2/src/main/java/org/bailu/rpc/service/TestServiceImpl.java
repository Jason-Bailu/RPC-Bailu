package org.bailu.rpc.service;

import org.bailu.rpc.TestService;
import org.bailu.rpc.annotation.RpcService;

/**
 * @description:
 * @Author: Xhy
 * @gitee: https://gitee.com/XhyQAQ
 * @copyright: B站: https://space.bilibili.com/152686439
 * @CreateTime: 2023-04-30 20:18
 */
@RpcService
public class TestServiceImpl implements TestService {
    @Override
    public void test(String key) {
        System.out.println("服务提供2 test 测试成功  :" + key);
    }

    @Override
    public void test2(String key) {
        System.out.println("服务提供2 tes2 测试成功  :" + key);
    }
}
