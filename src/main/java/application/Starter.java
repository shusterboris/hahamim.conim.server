package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import application.services.UserDetailsServiceImpl;

@SpringBootApplication
public class Starter implements ApplicationRunner {
	@Autowired
	UserDetailsServiceImpl userService;

	public static void main(String[] args) {
		SpringApplication.run(application.Starter.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// выполняется при запуске приложения
	}

}
