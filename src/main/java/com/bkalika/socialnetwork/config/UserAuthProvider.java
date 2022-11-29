package com.bkalika.socialnetwork.config;

import com.bkalika.socialnetwork.dto.CredentialsDto;
import com.bkalika.socialnetwork.dto.UserDto;
import com.bkalika.socialnetwork.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author @bkalika
 */
@Component
public class UserAuthProvider implements AuthenticationProvider {

    private final AuthenticationService authenticationService;

    public UserAuthProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDto userDto = null;
        if(authentication instanceof UsernamePasswordAuthenticationToken) {
           userDto = authenticationService.authenticate(new CredentialsDto(
                   (String) authentication.getPrincipal(),
                   (char[]) authentication.getCredentials()
           ));
        } else if(authentication instanceof PreAuthenticatedAuthenticationToken) {
            userDto = authenticationService.findByToken((String) authentication.getPrincipal());
        }

        if(userDto == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDto, null, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
