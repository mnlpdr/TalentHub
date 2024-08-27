package br.edu.ifpb.pweb2.talenthub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Criação do usuário "mock" coordenador01
        UserDetails user = User.withUsername("coordenador01")
                .password(passwordEncoder().encode("coordenador01"))
                .roles("COORDENADOR")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/coordenador/login").permitAll() // Permite acesso à página de login sem autenticação
                                .requestMatchers("/coordenador/**").hasRole("COORDENADOR") // Protege as URLs que começam com /coordenador/**
                                .anyRequest().permitAll() // Permite acesso a todas as rotas sem autenticação

                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/coordenador/login")
                                .loginProcessingUrl("/coordenador/login") // URL onde o login é processado
                                .defaultSuccessUrl("/coordenador/ofertas", true)
                                .failureUrl("/coordenador/login?error=true")
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/coordenador/login?logout=true")
                )
                                //.disable()); // Desabilita a página de login padrão
                .csrf(csrf -> csrf.disable());
        return http.build();

    }


}
