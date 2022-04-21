package com.training.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC Configuration
 * 
 * @author Red Devil
 * @since 2020-02-20
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// Resources controlled
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/", "file:D:/java/WorkSpace5/sample-project")
				.setCacheControl(CacheControl.maxAge(10, TimeUnit.DAYS).cachePublic());
	}
}