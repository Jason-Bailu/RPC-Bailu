package org.bailu.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcReference {
    // 版本
    String serviceVersion() default "1.0";
    // 超时时间
    long timeout() default 5000;
    // 负载均衡策略
    String loadBalancer();
    // 容错机制策略
    String faultTolerant();
    // 重试次数
    long retryCount() default 3;
}
