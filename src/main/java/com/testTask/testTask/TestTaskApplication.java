package com.testTask.testTask;

import com.testTask.testTask.models.Company;
import com.testTask.testTask.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.testTask.testTask.shared.Utils;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;

import java.util.List;


@SpringBootApplication
public class TestTaskApplication {

	@Autowired
	private CompanyService service;

	public static void main(String[] args) {

		SpringApplication.run(TestTaskApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public ResponseEntity<String> initializeH2AfterStartup() {
		Company company = Company.builder()
				.name(Utils.getCompanyName())
				.id(1)
				.build();
		this.service.save(company);
		List<Company> companies = service.findAll();
		if(companies != null) {
			return ResponseEntity.ok().body("Database initialized");
		} else {
			return ResponseEntity.ok().body("Something bad happened :(");
		}
	}

}
