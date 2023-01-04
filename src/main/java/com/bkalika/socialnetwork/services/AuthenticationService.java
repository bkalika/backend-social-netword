package com.bkalika.socialnetwork.services;

import com.bkalika.socialnetwork.dto.CredentialsDto;
import com.bkalika.socialnetwork.dto.UserDto;
import com.bkalika.socialnetwork.entities.User;
import com.bkalika.socialnetwork.entities.UserStatus;
import com.bkalika.socialnetwork.mappers.UserMapper;
import com.bkalika.socialnetwork.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

/**
 * @author @bkalika
 */
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    @Value("${auth.cookie.hmac-key:secret-key}")
    private String secretKey;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    @Transactional
    public UserDto authenticate(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new RuntimeException("User not found"));
//        String encodeMasterPassword = passwordEncoder.encode(CharBuffer.wrap("the-password"));
        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());

            return userMapper.toUserDto(user);
        }
        throw new RuntimeException("Invalid password");
    }

    public UserDto findByLogin(String login) {
        if("login".equals(login)) {
            return new UserDto(1L, "Bohdan", "Kalika", "login", "token",
                    UserStatus.CREATED, 25);
        }
        throw new RuntimeException("Invalid login");
    }

    public String createToken(UserDto userDto) {
        return userDto.getId() + "&" + userDto.getLogin() + "&" + calculateHmac(userDto);
    }

    public UserDto findByToken(String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        String[] parts = token.split("&");

        Long userId =  Long.valueOf(parts[0]);
        String login = parts[1];
        String hmac = parts[2];
        UserDto userDto = findByLogin(login);
        if(!hmac.equals(calculateHmac(userDto)) || !userId.equals(userDto.getId())) {
            throw new RuntimeException("Invalid Cookie value");
        }

        return userDto;
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
}
