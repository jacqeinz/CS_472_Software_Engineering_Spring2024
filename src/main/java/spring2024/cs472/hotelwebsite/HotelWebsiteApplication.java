package spring2024.cs472.hotelwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class HotelWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelWebsiteApplication.class, args);
	}

}
