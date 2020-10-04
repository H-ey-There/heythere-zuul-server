package com.heythere.zuul;

import com.heythere.zuul.security.jwt.JwtProperties;
import com.heythere.zuul.security.payload.SignUpRequest;
import com.heythere.zuul.user.model.User;
import com.heythere.zuul.user.repository.UserRepository;
import com.heythere.zuul.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@EnableEurekaClient
@EnableZuulProxy
@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class HeythereZuulServerApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(HeythereZuulServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userService.registerUser(new SignUpRequest("admin", "admin@admin", "123"));
	}
}
