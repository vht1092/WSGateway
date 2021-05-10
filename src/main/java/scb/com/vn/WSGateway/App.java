package scb.com.vn.WSGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableCaching
@EncryptablePropertySource(name = "EncryptedProperties", value = "classpath:encrypted.properties")
public class App extends SpringBootServletInitializer {
	
	public static void main( String[] args ) {
    	SpringApplication.run(App.class, args);
    }
}
