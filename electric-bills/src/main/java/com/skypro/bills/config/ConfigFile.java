package com.skypro.bills.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ConfigFile {
    protected final static double priceForKW = 1.05;

}
