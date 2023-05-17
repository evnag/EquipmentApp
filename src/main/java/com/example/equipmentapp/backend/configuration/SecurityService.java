package com.example.equipmentapp.backend.configuration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    private static final String LOGIN_SUCCESS_URL = "/";
    private static final String LOGOUT_SUCCESS_URL = "/login";
    public final static String PAS_PREFIX = "{bcrypt}";
    public final static int PAS_PREFIX_COUNT = PAS_PREFIX.length();
    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;

    public SecurityService(@Qualifier("jdbcUserDetailsManager") UserDetailsManager manager) {
        this.manager = manager;
        this.encoder = new BCryptPasswordEncoder();
    }

    public boolean login(String userName, String password) throws UsernameNotFoundException {
        if (!manager.userExists(userName)) {
            throw new UsernameNotFoundException("User not found!");
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(PAS_PREFIX_COUNT);
        boolean isLoggedIn = encoder.matches(password, encryptedPasswordWithoutEncryptionType);
        if (isLoggedIn) {
            UI.getCurrent().navigate(LOGIN_SUCCESS_URL);
        }
        return isLoggedIn;
    }

    public String encodePassword(String password) {
        return PAS_PREFIX + encoder.encode(password);
    }

    public UserDetails getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) context.getAuthentication().getPrincipal();
        }
        return null;
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                null);
    }
}
