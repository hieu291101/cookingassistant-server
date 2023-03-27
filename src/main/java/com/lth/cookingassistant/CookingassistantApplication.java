package com.lth.cookingassistant;


import com.lth.cookingassistant.model.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableConfigurationProperties(AppProperties.class)
public class CookingassistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookingassistantApplication.class, args);
	}

}
