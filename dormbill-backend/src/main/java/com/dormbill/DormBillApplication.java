package com.dormbill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用入口：启动 Spring Boot，自动扫描并加载所有组件。
 */
@SpringBootApplication
public class DormBillApplication {

    public static void main(String[] args) {
        SpringApplication.run(DormBillApplication.class, args);
    }
}
