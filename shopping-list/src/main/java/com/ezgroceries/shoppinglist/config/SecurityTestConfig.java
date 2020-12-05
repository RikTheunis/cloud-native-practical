package com.ezgroceries.shoppinglist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("test")
public class SecurityTestConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    // Only authenticated users are allowed to de shopping list api calls
                    .mvcMatchers("/shopping-lists").hasRole("USER")
                    .mvcMatchers("/shopping-lists/**").hasRole("USER")
                    // Non authenticated users are still allowed to search cocktails
                    .mvcMatchers("/cocktails").permitAll()
                    .mvcMatchers("/cocktails/**").permitAll()
                    .anyRequest().authenticated()

                .and()
                    .httpBasic();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser("user1")
                    .password(passwordEncoder.encode("MAGA2020"))
                    .roles("USER")
                    .and()
                .withUser("user2")
                    .password(passwordEncoder.encode("MAGA2020"))
                    .roles("USER");

    }

}
