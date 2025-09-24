package vn.iotstar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


import vn.iotstar.Service.IStorageService;
import vn.iotstar.config.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class) 

public class BtThymeleaf1Application {

	public static void main(String[] args) {
		SpringApplication.run(BtThymeleaf1Application.class, args);
	}
	@Bean
	CommandLineRunner init(IStorageService storageService) {
	return (args -> {
		storageService.init();
	});
	}




}
