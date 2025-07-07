package com.rewardapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rewardapp.rewardservice.RewardServiceImpl;


/*Entry point for the Reward Program Spring Boot application.*/

@SpringBootApplication
public class RewardProgramApplication {	
	
	public static void main(String[] args) {
		SpringApplication.run(RewardProgramApplication.class, args);
		
	}

}
