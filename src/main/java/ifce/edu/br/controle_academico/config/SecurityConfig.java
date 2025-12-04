package ifce.edu.br.controle_academico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/css/**", "/js/**", "/h2-console/**").permitAll()
                        .requestMatchers("/register/**").hasAnyRole("ADMIN", "SECRETARIA")
                        .requestMatchers("/disciplinas/**").hasRole("ADMIN")
                        .requestMatchers("/alunos/**", "/matriculas/**")
                        .hasAnyRole("ADMIN", "SECRETARIA")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        // necessário para o H2 console
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        return http.build();
    }
}