package com.springsecuritykm.demo.springsecuritykmdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder managerBuilder)
            throws Exception {
        managerBuilder
                /*.inMemoryAuthentication()
                .withUser("user").password("pass").roles("USER")
                .and()
                .withUser("user2").password("pass2").roles("USER");*/
                .userDetailsService(userDetailsService);

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/home/**").hasRole("USER")
                .antMatchers("/").anonymous()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/register/user/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/user").defaultSuccessUrl("/home")

                .and()
                .logout()
                .permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"));

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

}
