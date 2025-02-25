package de.dfki.scilake.dostres;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jmschnei - Julian Moreno Schneider
 * Class executing the Spring Boot Application
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
//@EnableConfigurationProperties(EConversionRestAPI.class)
//@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,WebMvcAutoConfiguration.class })
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan(basePackages = {"de.dfki.scilake.dostres"})
public class DOSTRESApplication {
	
	Logger logger = Logger.getLogger(DOSTRESApplication.class.getName());

	/**
	 * Main class starting the Spring Application
	 * @param args
	 */
	public static void main(String[] args) {
        SpringApplication.run(DOSTRESApplication.class, args);
    }

}

