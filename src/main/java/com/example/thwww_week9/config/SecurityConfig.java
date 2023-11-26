package com.example.thwww_week9.config;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    public void configSecurityWeb (AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder ) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser(User.withUsername("Gnoodd")
//                .password(passwordEncoder.encode("Gnoodd"))
//                .roles("ADMIN")
//                .build())
//        .withUser(User.withUsername("Dong")
//                .password(passwordEncoder.encode("Dong"))
//                .roles("User")
//                .build());
//
//    }
    @Autowired
    public void configSecurityWeb (AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder, DataSource dataSource) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
            .withUser(User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build())
        .withUser(User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("User")
                .build());

    }



    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth
                                    .requestMatchers("/", "/home", "/index").hasRole("ADMIN")
                                    .requestMatchers("/api/**").hasRole("USER")
//                                    .requestMatchers("/h2-console/**").permitAll()
                                    .requestMatchers("/api/v1/auth/**", "/v2/api-docs/**", "/v3/api-docs/**",
                                            "/swagger-resources/**", "/swagger-ui/**", "/webjars/**").permitAll()
                                    .anyRequest().authenticated())
                    .csrf(csrf-> csrf.ignoringRequestMatchers("/h2-console/**"))
                    .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                    .httpBasic(Customizer.withDefaults());



        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }



}
