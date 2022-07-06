package com.nttdata.proyecto01.typeclientservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TypeclientServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(TypeclientServiceApplication.class, args);
  }

}
