package com.itechart.pathfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PathfinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PathfinderApplication.class, args);
	}

}
