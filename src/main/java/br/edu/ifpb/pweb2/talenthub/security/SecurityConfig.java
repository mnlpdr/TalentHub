package br.edu.ifpb.pweb2.talenthub.security;

import javax.sql.DataSource;

import br.edu.ifpb.pweb2.talenthub.service.CustomUserDetailsService;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/imagens/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin((form)->form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home",true)
                        .permitAll())
                .logout((logout) -> logout.logoutUrl("/auth/logout"));
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
            UserDetails a1 = User.withUsername("geraldo").password(passwordEncoder().encode("geraldo")).roles("ALUNO").build();
        UserDetails a2 = User.withUsername("pedro").password(passwordEncoder().encode("pedro")).roles("ALUNO").build();
        UserDetails emp1 = User.withUsername("ifood").password(passwordEncoder().encode("ifood")).roles("EMPRESA").build();
        UserDetails emp2 = User.withUsername("embraer").password(passwordEncoder().encode("embraer")).roles("EMPRESA").build();
        UserDetails coord1 = User.withUsername("coordenador1").password(passwordEncoder().encode("coordenador1")).roles("COORDENADOR").build();
        UserDetails coord2 = User.withUsername("coordenador2").password(passwordEncoder().encode("coordenador2")).roles("COORDENADOR").build();

        // Evita duplicação dos usuários no banco
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        if (!users.userExists(a1.getUsername())) {
            users.createUser(a1);
            users.createUser(a2);
            users.createUser(emp1);
            users.createUser(emp2);
            users.createUser(coord1);
            users.createUser(coord2);
        }
        return users;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}

