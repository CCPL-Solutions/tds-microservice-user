package co.com.viveres.susy.microserviceuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = {"co.com.viveres.susy"})
public class MicroserviceProductApplication implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceProductApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Contraseña usuario root");
        System.out.println(this.passwordEncoder.encode("tds_root"));
    }
}
