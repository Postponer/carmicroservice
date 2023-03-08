package ua.com.foxminded.carmicroservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@ComponentScan(basePackages = "ua.com.foxminded.carmicroservice.configuration")
public class SecurityConfig {

    private final KeycloakLogoutHandler keycloakLogoutHandler;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/**").authenticated()
				.requestMatchers(HttpMethod.PUT, "/api/**").authenticated()
				.requestMatchers(HttpMethod.DELETE, "/api/**").authenticated()
				.anyRequest().permitAll()
				.and()
				.oauth2Login()
				.and()
				.logout()
				.addLogoutHandler(keycloakLogoutHandler)
				.logoutSuccessUrl("/")
				.and()
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				.build();

	}

	@Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
        
    }

}
