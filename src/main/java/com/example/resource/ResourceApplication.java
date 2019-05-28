package com.example.resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.servicecomb.springboot.starter.provider.EnableServiceComb;

@SpringBootApplication
@EnableServiceComb
@MapperScan(basePackages = "com.example.resource.dao")
public class ResourceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ResourceApplication.class, args);
	}
}
