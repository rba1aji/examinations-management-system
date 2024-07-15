package com.rba1aji.examinationmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.rba1aji.examinationmanagementsystem.repository")
@EnableScheduling
public class ExaminationManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExaminationManagementSystemApplication.class, args);
	}

}
