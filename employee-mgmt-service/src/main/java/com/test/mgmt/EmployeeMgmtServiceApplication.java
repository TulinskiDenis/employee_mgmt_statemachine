package com.test.mgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class EmployeeMgmtServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeMgmtServiceApplication.class, args);
    }

}
