package com.example.carclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") //tell spring to enable auditing and leavarage the bean of class AuditawareIMpl
public class CarclinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarclinicApplication.class, args);
	}

}
