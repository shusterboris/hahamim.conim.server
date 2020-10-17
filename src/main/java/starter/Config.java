package starter;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import controllers.RestQueryController;

@SpringBootConfiguration
@ComponentScan(basePackageClasses = RestQueryController.class)
public class Config implements WebMvcConfigurer {

}
