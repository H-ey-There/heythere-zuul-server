package com.heythere.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableEurekaClient
@EnableZuulProxy
@EnableJpaAuditing
@SpringBootApplication
public class HeythereZuulServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(HeythereZuulServerApplication.class, args);
	}

}
