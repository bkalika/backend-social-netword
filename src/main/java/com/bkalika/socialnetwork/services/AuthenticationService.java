package com.bkalika.socialnetwork.services;

import com.bkalika.socialnetwork.dto.CredentialsDto;
import com.bkalika.socialnetwork.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

/**
 * @author @bkalika
 */
@Service
public class AuthenticationService {

    @Value("${auth.cookie.hmac-key:secret-key}")
    private String secretKey;

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

    public String createToken(UserDto userDto) {
        return userDto.getId() + "&" + userDto.getLogin() + "&" + calculateHmac(userDto);
    }

    public UserDto findByToken(String token) {
        String[] parts = token.split("&");

        Long userId =  Long.valueOf(parts[0]);
        String login = parts[1];
        String hmac = parts[2];
        UserDto user = findByLogin(login);
        if(!hmac.equals(calculateHmac(user)) || !userId.equals(user.getId())) {
            throw new RuntimeException("Invalid Cookie value");
        }

        return user;
    }

    private String calculateHmac(UserDto userDto) {
        byte[] secretKeyBytes = Objects.requireNonNull(secretKey)
                .getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = (userDto.getId() + "&" + userDto.getLogin())
                .getBytes(StandardCharsets.UTF_8);

        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec sec = new SecretKeySpec(secretKeyBytes, "HmacSHA512");
            mac.init(sec);
            byte[] hmacBytes = mac.doFinal(valueBytes);
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException();
        }
    }

    public UserDto findOrCreateByLogin(OAuth2User principal) {
        return new UserDto(1L, principal.getAttribute("name"), principal.getAttribute("login"));
    }
}
