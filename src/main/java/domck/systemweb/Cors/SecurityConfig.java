package domck.systemweb.Cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite todas las solicitudes
                )
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Desactiva frameOptions (alternativa moderna)
                );
        return http.build();
    }
}