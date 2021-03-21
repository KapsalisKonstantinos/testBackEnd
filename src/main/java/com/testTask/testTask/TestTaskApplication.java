package com.testTask.testTask;

import com.testTask.testTask.models.Company;
import com.testTask.testTask.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.testTask.testTask.shared.Utils;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.PostLoad;
import java.util.ArrayList;
import java.util.List;

//@RestController
//@RequestMapping(value = "")
@SpringBootApplication
public class TestTaskApplication {

	@Autowired
	private CompanyService service;

	public static void main(String[] args) {

		SpringApplication.run(TestTaskApplication.class, args);
		System.out.println(args[0].split("=")[1].concat(" ???"));
		if(!args[0].split("=")[1].isEmpty()) {
			Utils.setCompanyName(args[0].split("=")[1]);
		}
	}

	@EventListener(ApplicationReadyEvent.class)
	public ResponseEntity<String> initializeH2AfterStartup() {
		System.out.println("Hello, I have just started up");
		System.out.println(Utils.getCompanyName().concat(" is the best of"));
		Company company = new Company();
		company.setId(2);
		company.setName(Utils.getCompanyName().concat(" is the best"));
		this.service.save(company);
		List<Company> companies = service.findAll();
		if(companies != null) {
			return ResponseEntity.ok().body("Database initialized");
		} else {
			return ResponseEntity.ok().body("Something bad happened :(");
		}
	}

//	@GetMapping
//	public ResponseEntity<String> initDatabase() {
//		this.service.save(new Company(2, Utils.getCompanyName()));
//		List<Company> companies = service.findAll();
//		if(companies != null) {
//			return ResponseEntity.ok().body("Database initialized");
//		} else {
//			return ResponseEntity.ok().body("Something bad happened :(");
//		}
//	}

}
