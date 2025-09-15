package com.lmscr.phonerepair;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lmscr.phonerepair.mapper")
public class PhoneRepairApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneRepairApplication.class, args);
    }
}
