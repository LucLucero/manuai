package com.sylvamo.ai.manuai;

import com.sylvamo.ai.manuai.modules.document.useCases.DocsReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManuaiApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		SpringApplication.run(ManuaiApplication.class, args);



	}

}
