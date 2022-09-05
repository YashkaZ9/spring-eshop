package com.baykov.springeshop.config;

import com.baykov.springeshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/auth/register").permitAll()
                .antMatchers(HttpMethod.GET, "/products/**").permitAll()
                .antMatchers(HttpMethod.GET, "/categories/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
//                .formLogin()
//                .loginPage("auth/login")
//                .loginProcessingUrl("/auth/login-process")
//                .defaultSuccessUrl("/")
//                .failureUrl("auth/login?error")
//                .and()
//                .logout()
//                .logoutUrl("/auth/logout")
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession()
//                .deleteCookies("JSESSIONID")
//                .clearAuthentication(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    protected DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
