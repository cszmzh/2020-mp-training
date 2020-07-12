package com.code515.shopping.configuration;

import com.code515.shopping.constant.URLConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        registry.addResourceHandler("/static/**").addResourceLocations("file:"+ URLConfig.getRealURL());

        super.addResourceHandlers(registry);

    }
}
