/**
 * HotelWebsiteApplication.java
 */
package spring2024.cs472.hotelwebsite;

// Imports necessary for the class
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Main application class for the hotel website.
 * This class initializes the Spring Boot application.
 * It excludes security auto-configuration to disable Spring Security.
 *
 * @author Team ABCFG
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class HotelWebsiteApplication {

	/**
	 * Main method to run the application.
	 *
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(HotelWebsiteApplication.class, args);
	}
}
