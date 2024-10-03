package br.edu.ifpb.pweb2.talenthub.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import br.edu.ifpb.pweb2.talenthub.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
            http
                            .authorizeHttpRequests(auth -> auth
                                            .requestMatchers("/css/**", "/imagens/**").permitAll()
                                            .requestMatchers("/login", "/error", "/alunos/cadastro",
                                                            "/empresas/cadastro")
                                            .permitAll() // Permitir acesso às rotas de cadastro
                                            .requestMatchers("/alunos/**").hasAnyRole("ALUNO", "COORDENADOR")
                                            .requestMatchers("/empresas/**").hasAnyRole("EMPRESA", "COORDENADOR")
                                            .requestMatchers("/empresas/deletar").hasRole("COORDENADOR")
                                            .requestMatchers("/ofertas/**").hasAnyRole("EMPRESA", "COORDENADOR")
                                            .requestMatchers("/coordenador/**").hasRole("COORDENADOR")
                                            .requestMatchers("/coordenadores/**").hasRole("COORDENADOR")
                                            .anyRequest().authenticated())
                            .formLogin(form -> form
                                            .loginPage("/login")
                                            .defaultSuccessUrl("/home", true)
                                            .permitAll())
                            .logout(logout -> logout
                                            .logoutUrl("/logout")
                                            .logoutSuccessUrl("/login?logout=true")
                                            .invalidateHttpSession(true)
                                            .deleteCookies("JSESSIONID"))
                            .exceptionHandling(exceptionHandling -> exceptionHandling
                                            .accessDeniedHandler(accessDeniedHandler()))
                            .csrf(csrf -> csrf.disable());

            return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        // Alguns usuários básicos, criados quando da 1a. execução da aplicaçao
        UserDetails a1 = User.withUsername("geraldo").password(passwordEncoder().encode("geraldo")).roles("ALUNO")
                .build();
        UserDetails a2 = User.withUsername("pedro").password(passwordEncoder().encode("pedro")).roles("ALUNO")
                .build();
        UserDetails emp1 = User.withUsername("ifood").password(passwordEncoder().encode("ifood")).roles("EMPRESA")
                .build();
        UserDetails emp2 = User.withUsername("embraer").password(passwordEncoder().encode("embraer")).roles("EMPRESA")
                .build();
        UserDetails coord1 = User.withUsername("coordenador1").password(passwordEncoder().encode("coordenador1"))
                .roles("COORDENADOR").build();

        // Evita duplicação dos usuários no banco
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        if (!users.userExists(a1.getUsername())) {
            users.createUser(a1);
            users.createUser(a2);
            users.createUser(emp1);
            users.createUser(emp2);
            users.createUser(coord1);
        }
        return users;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService);
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(); // Criptografa as senhas
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
            AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
            accessDeniedHandler.setErrorPage("/error"); // Define a página de erro
            return accessDeniedHandler;
    }

} 
