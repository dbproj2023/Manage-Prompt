package com.dbproj.manageprompt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 로그아웃이 없음
    // 일단 빈 기능만 추가해둠
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.formLogin()
                .loginPage("/api/v1/auth/login")
                .defaultSuccessUrl("/")
                .usernameParameter("id")
                .failureUrl("/api/v1/auth/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/auth/logout"))
                .logoutSuccessUrl("/");

        httpSecurity.authorizeRequests()
                .antMatchers("/api/v1/auth").permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
