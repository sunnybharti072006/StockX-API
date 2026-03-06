package com.TradeEngine.StockXAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StockXApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockXApiApplication.class, args);
	}

}
