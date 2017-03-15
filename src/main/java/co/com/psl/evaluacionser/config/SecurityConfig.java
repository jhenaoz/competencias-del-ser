package co.com.psl.evaluacionser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This class implements the security for the URI to request the reports.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${securityUsername}")
    private String username;

    @Value("${securityPassword}")
    private String pass;

    /**
     * This method validates the user login.
     * @param auth Authenticator
     * @throws Exception if the authentication fails
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
        .inMemoryAuthentication()
        .withUser(username).password(pass).roles("ADMIN");
    }

    /**
     *
     * @param http an http security
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception { // @formatter:off
        http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/survey/report/**").hasRole("ADMIN")
        .and().formLogin()
        .and().logout();
    }
}
