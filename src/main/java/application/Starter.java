package application;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import application.entities.security.Authority;
import application.entities.security.User;
import application.services.UserDetailsServiceImpl;

@SpringBootApplication
@EnableAutoConfiguration
public class Starter implements ApplicationRunner{
	@Autowired
	UserDetailsServiceImpl userService;
	
	public static void main(String[] args) {
		SpringApplication.run(application.Starter.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//User user = new User("aron", "123");
		//userService.saveUser(user);
		//user = new User("may", "123");
		//userService.saveUser(user);
		//user = new User("vova", "123");
		//userService.saveUser(user);		
	}	
}
