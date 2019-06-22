package com.dh.webservice.security;

import com.dh.webservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = false)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String userQuery;
    @Value("${spring.queries.roles-query}")
    private String rolesQuery;
    @Autowired
    private MemberService memberService;

    @Autowired
    private WebSecurityHandler securityHandler;

    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;

    }


    @Override
    public void configure(WebSecurity web) throws Exception { // security resources 허가
        web.ignoring().antMatchers("/resources/static/**", "/css/**", "/images/**","/js/**");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth
//                .userDetailsService(userDetailsServiceImpl)
                .authenticationProvider(authenticationProvider())
                .jdbcAuthentication()
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/member/signup").permitAll()
                    .antMatchers("/member/admin/**").hasAuthority("ADMIN").anyRequest().authenticated()
                .and()
                    .csrf().disable()
                .formLogin()
                    .usernameParameter("user_email")
                    .passwordParameter("user_pwd")
                    .loginPage("/login")
                    .successHandler(securityHandler)
                    .failureHandler(securityHandler)
//                .failureUrl("/login?error=true")
//                .defaultSuccessUrl("/member/admin/index")
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .and()
                        .exceptionHandling().accessDeniedPage("/access-denied");
    }



}