package com.securefileshare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Basic HTTP security configuration
        http
                .csrf().disable()  // Disable CSRF protection for simplicity, enable it for production apps
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/files/**").hasAuthority("ROLE_USER")  // Only users with ROLE_USER can view files
                .antMatchers(HttpMethod.POST, "/files/upload").hasAuthority("ROLE_ADMIN")  // Only admins can upload files
                .anyRequest().authenticated()  // Any other request requires authentication
                .and()
                .formLogin()  // Enable form login
                //.loginPage("/login")  // Custom login page if needed
                .permitAll()
                .and()
                .logout()  // Enable logout functionality
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder used for encoding passwords
        return new BCryptPasswordEncoder();
    }
}

