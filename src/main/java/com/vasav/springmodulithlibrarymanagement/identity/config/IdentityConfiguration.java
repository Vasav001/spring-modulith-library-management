package com.vasav.springmodulithlibrarymanagement.identity.config;

import com.vasav.springmodulithlibrarymanagement.identity.security.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class IdentityConfiguration {
}