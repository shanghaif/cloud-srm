package com.midea.cloud.gernator.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public SecurityConfig() {
    }

    protected void configure(HttpSecurity http) throws Exception {
        ((HttpSecurity)((HttpSecurity)http.csrf().disable()).httpBasic().disable()).formLogin().disable();
    }
}