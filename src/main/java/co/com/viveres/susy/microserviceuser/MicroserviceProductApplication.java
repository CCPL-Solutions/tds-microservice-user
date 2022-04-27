package co.com.viveres.susy.microserviceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEurekaClient
@SpringBootApplication
@EntityScan(basePackages = {
		"co.com.viveres.susy.microserviceuser.entity", 
		"co.com.viveres.susy.microservicecommons.entity"})
@EnableJpaRepositories(basePackages = {
    "co.com.viveres.susy.microserviceuser.repository", 
	"co.com.viveres.susy.microservicecommons.repository"})
public class MicroserviceProductApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicroserviceProductApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
