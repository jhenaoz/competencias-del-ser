package co.com.psl.evaluacionser.config;

import co.com.psl.evaluacionser.persistence.AdministratorRepository;
import co.com.psl.evaluacionser.service.dto.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class implements the security for the URI to request the reports.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_ROLE = "ADMIN";

    private String username;
    private String password;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdministratorRepository administratorRepository;

    /**
     * This method validates the user login.
     *
     * @param auth Authenticator
     * @throws Exception if the authentication fails
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
        .inMemoryAuthentication()
        .passwordEncoder(passwordEncoder)
        .withUser(username).password(password).roles(ADMIN_ROLE);
    }

    /**
     * Configure the HttpSecurity. <br>
     * Specifies the URIs that require authentication.
     *
     * @param http an http security
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/survey/report/**").hasRole(ADMIN_ROLE)
        .and().formLogin()
        .and().logout();
    }

    @Autowired
    private void initUser() {
        Administrator administrator = administratorRepository.findAdministrator();
        this.password = administrator.getPassword();
        this.username = administrator.getUsername();
    }
}
