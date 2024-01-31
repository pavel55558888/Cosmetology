package com.example.cosmetology.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@ComponentScan("com.example.cosmetology")
public class SecurityConfig {
    @Autowired
    DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/orders","/login","/login-error","/reg","/reg/**","/orders/*",
                                "/articles/*", "/orders/search/user","/recovery/**").permitAll()
                        .requestMatchers("/main.css", "/main.js","/image/**").permitAll()

                        .requestMatchers("/logout").authenticated()

                        .requestMatchers("/basket","/orders/{id}/basket","/mail","/send-email").hasAnyAuthority("USER", "DEVELOPER")

                        .requestMatchers("/controlpanel/newarticles","/controlpanel/newarticles/**",
                                "/controlpanel/dailyprofit", "/controlpanel/dailyprofit/**",
                                "/controlpanel/neworder","/controlpanel/neworder/**",
                                "/controlpanel/neworderpersonal","/controlpanel/neworderdpersonal/**",
                                "/controlpanel/orderpersonal", "/controlpanel/orderprsonal/**","/controlpanel/orderpersonal/**",
                                "/articles","/articles/*",
                                "/","/orders","/orders/*","/calendar/add","/calendar/**","/orders/search/**").hasAnyAuthority("DEVELOPER", "ADMIN", "EMPLOYEE")

                        .requestMatchers("/controlpanel/**","/orders/**", "/articles/**","/expired-product-personal",
                                "/expired-product","/monthly-report").hasAnyAuthority("DEVELOPER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .failureUrl("/login-error")
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("SELECT username,password,enable FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT users.username, user_role.roles FROM users " +
                        "INNER JOIN user_role ON users.id = user_role.user_id WHERE username=?");
    }



    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
