package com.hepsiburada.ecommerce;

import com.hepsiburada.ecommerce.service.ProcessCommandService;
import com.hepsiburada.ecommerce.utils.FileOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner {

	@Autowired
	ProcessCommandService processCommandService;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Override
	public void run(String...args) {
		FileOperations operations = new FileOperations(processCommandService);
		operations.setInputFile(args[0]);
		operations.setOutputFile(args[1]);
		operations.readFile();
	}



}
