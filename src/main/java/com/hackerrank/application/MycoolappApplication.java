package com.hackerrank.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.hackerrank.property.FileStorageProperties;

@SpringBootApplication
@ComponentScan({"com.hackerrank"})
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class MycoolappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MycoolappApplication.class, args);
	}
}
