package com.bkalika.socialnetwork.services;

import com.bkalika.socialnetwork.dto.CredentialsDto;
import com.bkalika.socialnetwork.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

/**
 * @author @bkalika
 */
@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto authenticate(CredentialsDto credentialsDto) {
        String encodeMasterPassword = passwordEncoder.encode(CharBuffer.wrap("the-password"));
        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), encodeMasterPassword)) {
            return new UserDto(1L, "Bohdan", "Kalika", "login", "token");
        }
        throw new RuntimeException("Invalid password");
    }

    public UserDto findByLogin(String login) {
        if("login".equals(login)) {
            return new UserDto(1L, "Bohdan", "Kalika", "login", "token");
        }
        throw new RuntimeException("Invalid login");
    }
}
