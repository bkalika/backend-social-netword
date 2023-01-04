package com.bkalika.socialnetwork.controllers;

import com.bkalika.socialnetwork.config.CookieAuthFilter;
import com.bkalika.socialnetwork.config.UserAuthenticationProvider;
import com.bkalika.socialnetwork.dto.SignUpDto;
import com.bkalika.socialnetwork.dto.UserDto;
import com.bkalika.socialnetwork.services.AuthenticationService;
import com.bkalika.socialnetwork.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author @bkalika
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class AuthenticationController {
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final AuthenticationService authenticationService;

    @PostMapping("/signIn")
    public ResponseEntity<UserDto> signIn(@AuthenticationPrincipal UserDto userDto,
                                          HttpServletResponse response) {
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getLogin()));

        Cookie cookie = new Cookie(CookieAuthFilter.COOKIE_NAME,
                authenticationService.createToken(userDto));

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge((int) Duration.of(1, ChronoUnit.DAYS).toSeconds());
        cookie.setPath("/");

        response.addCookie(cookie);

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpDto user) {
        UserDto createUser = userService.signUp(user);
        return ResponseEntity.created(URI.create("/users/" + createUser.getId() + "/profile")).body(createUser);
    }

    @PostMapping("/signOut")
    public ResponseEntity<Void> signOut(HttpServletRequest request) {
        SecurityContextHolder.clearContext();

        Optional<Cookie> authCookie = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> CookieAuthFilter.COOKIE_NAME
                        .equals(cookie.getName()))
                .findFirst();

        authCookie.ifPresent(cookie -> cookie.setMaxAge(0));

        return ResponseEntity.noContent().build();
    }
}
