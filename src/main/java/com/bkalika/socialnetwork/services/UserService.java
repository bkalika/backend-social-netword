package com.bkalika.socialnetwork.services;

import com.bkalika.socialnetwork.dto.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author @bkalika
 */
@Service
public class UserService {

    public ProfileDto getProfile(Long userId) {
        return new ProfileDto(new UserSummaryDto(1L, "Bohdan", "Kalika"),
                List.of(new UserSummaryDto(2L, "John", "Doe")),
                List.of(new MessageDto(1L, "My message")),
                Arrays.asList(new ImageDto(1L, "Titile", null))
        );
    }

    public void addFriend(Long friendId) {
        // nothing to do at the moment
    }

    public List<UserSummaryDto> searchUsers(String term) {
        return Arrays.asList(new UserSummaryDto(1L, "Bohdan", "Kalika"),
                new UserSummaryDto(2L, "John", "Doe"));
    }

    public UserDto signUp(SignUpDto user) {
        return new UserDto(1L, "Bohdan", "Kalika", "login", "token");
    }

    public void signOut(UserDto user) {
        // nothing to do at the moment
    }
}
