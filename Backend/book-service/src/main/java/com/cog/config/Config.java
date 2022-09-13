package com.cog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.client.RestTemplate;
@Configuration
public class Config {
	/*@Bean
	public  PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
