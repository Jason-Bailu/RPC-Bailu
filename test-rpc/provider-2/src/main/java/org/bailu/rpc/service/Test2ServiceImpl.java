package org.bailu.rpc.service;

import org.bailu.rpc.Test2Service;
import org.bailu.rpc.annotation.RpcService;

/**
 * @description:
 * @Author: Xhy
 * @gitee: https://gitee.com/XhyQAQ
 * @copyright: B站: https://space.bilibili.com/152686439
 * @CreateTime: 2023-05-09 10:46
 */
@RpcService
public class Test2ServiceImpl implements Test2Service {
    @Override
    public String test(String key) {
        System.out.println("服务提供2 test2 测试成功 :" + key);
        return key;
    }
}
