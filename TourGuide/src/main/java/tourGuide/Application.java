package tourGuide;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import tourGuide.service.RewardsService;


@SpringBootApplication
@EnableFeignClients("tourGuide")
public class Application {

    public static void main(String[] args) {
        // SpringApplication.run(Application.class, args);
        
    	ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		
		// Connexion test of SpringData JPA
		RewardsService rewardsService = context.getBean(RewardsService.class);
		UUID userId= UUID.randomUUID();
    }

}
