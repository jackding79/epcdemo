package cn.sunline.myrpc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ConsumerApplication.class, args);
        System.in.read();
    }

}
