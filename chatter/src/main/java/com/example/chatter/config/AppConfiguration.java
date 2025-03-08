package com.example.chatter.config;

import java.nio.file.Paths;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfiguration {
    private String uploadPath;

    public String getFullUploadPath() {
        return Paths.get(uploadPath).toAbsolutePath().toString();
    }
}
