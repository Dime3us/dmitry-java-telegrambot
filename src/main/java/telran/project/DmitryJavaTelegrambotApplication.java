package telran.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DmitryJavaTelegrambotApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmitryJavaTelegrambotApplication.class, args);
	}

}
