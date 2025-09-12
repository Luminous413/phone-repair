package com.lmscr.testspring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lmscr.testspring.mapper")
public class PhoneRepairApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneRepairApplication.class, args);
    }
}
