package com.costaT.Todo_List_Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TodoListProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListProjectApplication.class, args);
	}

}
