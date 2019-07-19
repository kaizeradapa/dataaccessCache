package com.dataaccessCache.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DataaccessCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataaccessCacheApplication.class, args);
	}

}
