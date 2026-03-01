package com.eCommerce.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer
{

    @Value("${frontend.url}")
    String frontEndUrl;

    @Value("${project.image:images/}")
    String imagePath;

    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173/",frontEndUrl,"http://localhost:8080/")
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Get the absolute path of the project directory
        String projectPath = System.getProperty("user.dir");
        String absoluteImagePath = projectPath + File.separator + "images";
        
        System.out.println("=== Image Resource Handler Configuration ===");
        System.out.println("Project Path: " + projectPath);
        System.out.println("Absolute Image Path: " + absoluteImagePath);
        System.out.println("Image Path from config: " + imagePath);
        System.out.println("============================================");
        
        // Serve images from the local images folder
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + absoluteImagePath + "/")
                .addResourceLocations("file:images/");
    }
}
