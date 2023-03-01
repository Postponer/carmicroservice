package ua.com.foxminded.carmicroservice.appconfiguration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableTransactionManagement
public class AppConfiguration implements WebMvcConfigurer {

	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();

	}

}