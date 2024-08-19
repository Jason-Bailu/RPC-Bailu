package org.bailu.rpc.annotation;

import org.bailu.rpc.proxy.provider.ProviderPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ProviderPostProcessor.class)
public @interface EnableProviderRpc {
}
