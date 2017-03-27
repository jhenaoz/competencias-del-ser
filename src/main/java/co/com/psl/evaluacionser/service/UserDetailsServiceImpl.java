package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.persistence.AdministratorRepository;
import co.com.psl.evaluacionser.service.dto.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a custom implementation of the UserDetailsService class, this class manages the admin username and
 * password using the persistence layer
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdministratorRepository administratorRepository;

    /**
     * This method take the credentials of the administrator from the database to check them with the login
     *
     * @param username username in case of multiple administrators
     * @return The administrator with the credentials
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Administrator administrator = administratorRepository.findAdministrator();
        if (administrator == null) {
            throw new UsernameNotFoundException("No user found with username: ");
        }
        return new User(administrator.getUsername(),
                administrator.getPassword(), getAuthorities(Collections.singletonList("ROLE_ADMIN")));
    }

    /**
     * This method casts the roles from a String List to a GrantedAuthority List to construct the User
     *
     * @param roles name of the role
     * @return the level of authority for the admin
     */
    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
