package co.com.psl.evaluacionser.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("userDetailsService")
public class AdminUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    private static String username;

    public static void setUsername(String username) {
        AdminUserDetailsService.username = username;
    }

    private static String password;

    public static void setPassword(String password) {
        AdminUserDetailsService.password = password;
    }

    private static String token;

    public static void setToken(String token) {

        AdminUserDetailsService.token = token;
    }

    private final Logger log = LoggerFactory.getLogger(AdminUserDetailsService.class);

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");

        return new User(AdminUserDetailsService.username, AdminUserDetailsService.password,getAuthorities(roles));
    }

    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }



}