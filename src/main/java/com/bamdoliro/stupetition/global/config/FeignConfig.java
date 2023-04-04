package com.bamdoliro.stupetition.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.bamdoliro.stupetition.global.feign")
public class FeignConfig {
}