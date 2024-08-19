package org.bailu.rpc;

import org.bailu.rpc.annotation.EnableConsumerRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableConsumerRpc
public class RpcConsumerDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpcConsumerDemoApplication.class, args);
    }
}
