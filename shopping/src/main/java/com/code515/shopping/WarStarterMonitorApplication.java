package com.code515.shopping;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class WarStarterMonitorApplication extends SpringBootServletInitializer {
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        //指向Application的SpringBoot启动类
        return builder.sources(ShoppingApplication.class);
    }
}
