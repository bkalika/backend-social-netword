package com.bkalika.socialnetwork.controllers;

import com.bkalika.socialnetwork.dto.ProfileDto;
import com.bkalika.socialnetwork.dto.UserDto;
import com.bkalika.socialnetwork.dto.UserSummaryDto;
import com.bkalika.socialnetwork.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author @bkalika
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ProfileDto> getUserProfile(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    @PostMapping("/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@AuthenticationPrincipal UserDto userDto,
                                          @PathVariable String friendId) {
        userService.addFriend(userDto, friendId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserSummaryDto>> searchUsers(@RequestParam(value = "term") String term) {
        return ResponseEntity.ok(userService.searchUsers(term));
    }
}
