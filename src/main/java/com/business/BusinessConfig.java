package com.business;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class BusinessConfig {

    public BusinessConfig() {
        super();
    }

    // Nothing else to be configured here: component scanning will do everything needed

}