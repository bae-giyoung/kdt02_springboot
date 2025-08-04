package edu.pnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@ImportResource("classpath:beans.xml")// IoC(Inversion of Control)
public class TestIoCApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestIoCApplication.class, args);
	}

}
