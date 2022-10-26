package oit.is.z1412.kaizi.janken.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class JankenAuthConfiguration {
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserBuilder users = User.builder();

    UserDetails user1 = users
        .username("user1")
        .password("$2y$10$0V8PNUXUFoYQcp.1/ULJsuoWTTfFV6AsRpY68cwaOF4E4KbUtjquG")
        .roles("USER")
        .build();
    UserDetails user2 = users
        .username("user2")
        .password("$2y$10$kRqeFw5XUhWogXxZ74MBxORb0R7qOX4nxzcL/ntoRsadX1Gvnwt96")
        .roles("USER")
        .build();
    UserDetails honda = users
        .username("ほんだ")
        .password("$2y$10$ARaO1JuL/fXlTcf6WMfIw.Yq62XP5SRWl/73pVAIjSVaAaQ4zwSxS")
        .roles("USER")
        .build();
    UserDetails igaki = users
        .username("いがき")
        .password("$2y$10$tm5NzO4jytVe.WibvdIV1eihp6u7WKN72dNI9z7hkDjbJFSFBqE9a")
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user1, user2, honda, igaki);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin();
    http.authorizeHttpRequests()
        .mvcMatchers("/janken/**").authenticated();
    http.logout().logoutSuccessUrl("/"); // ログアウト時は "http://localhost:8000/" に戻る

    http.csrf().disable();
    http.headers().frameOptions().disable();
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
