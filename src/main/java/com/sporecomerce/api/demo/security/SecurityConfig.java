package com.sporecomerce.api.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 
 * https://www.baeldung.com/securing-a-restful-web-service-with-spring-security
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RESTAuthenticationEntryPoint entryPoint;

    @Autowired
    private RESTAuthSuccessHandler successHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    CustomPlayerDetailService customPlayerDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication()
        // .withUser("alice").password(encoder().encode("123")).roles("ADMIN")
        // .and()
        // .withUser("bob").password(encoder().encode("123")).roles("USER")
        // .and()
        // .withUser("carla").password(encoder().encode("123")).roles("USER");

        auth.userDetailsService(customPlayerDetailService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                // Uncomment this to enable H2 console
                .headers().frameOptions().disable().and()
                ///
                .exceptionHandling().authenticationEntryPoint(entryPoint).and().authorizeRequests()
                .antMatchers("/public/**", "/login/**", "/**").permitAll()
                // Uncomment this to enable H2 console
                .antMatchers("/h2/**").permitAll()
                ////

                // .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated().and().formLogin().successHandler(successHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler()).and().logout()
                .logoutSuccessHandler(logoutSuccessHandler).and()
                // Uncomment this to enable H2 console
                .headers().frameOptions().disable()
        ///
        ;
    }

    /**
     * https://www.baeldung.com/spring-bean-annotations
     * 
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        // ver https://stackoverflow.com/a/42053745
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
