package org.bailu.rpc;

import org.bailu.rpc.annotation.EnableProviderRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProviderRpc
public class RpcProvider2Application {
    public static void main(String[] args) {
        SpringApplication.run(RpcProvider2Application.class, args);
    }
}
