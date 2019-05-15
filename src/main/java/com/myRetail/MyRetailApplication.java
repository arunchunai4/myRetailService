package com.myRetail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * myRetail spring boot application starter class
 * @author Arunchunai vendan Pugalenthi
 *
 */

@SpringBootApplication
public class MyRetailApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(MyRetailApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MyRetailApplication.class, args);
		LOGGER.info(" :::: Started MyRetail Service :::: ");
	}

}
