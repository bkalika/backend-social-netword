package com.bkalika.socialnetwork.services;

import com.bkalika.socialnetwork.dto.*;
import com.bkalika.socialnetwork.entities.User;
import com.bkalika.socialnetwork.mappers.UserMapper;
import com.bkalika.socialnetwork.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.*;

/**
 * @author @bkalika
 */
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public ProfileDto getProfile(String userId) {
        User user = getUser(Long.valueOf(userId));
        return userMapper.userToProfileDto(user);
    }

    public void addFriend(UserDto userDto, String friendId) {
        User user = getUser(userDto.getId());
        User friend = getUser(Long.valueOf(friendId));
        if(user.getFriends() == null)
            user.setFriends(new ArrayList<>());
        user.getFriends().add(friend);
        userRepository.save(user);
    }

    public List<UserSummaryDto> searchUsers(String term) {
        List<User> users = userRepository.search(term);
        List<UserSummaryDto> usersToBeReturned = new ArrayList<>();

        users.forEach(user ->
                usersToBeReturned.add(new UserSummaryDto(user.getId(), user.getFirstName(), user.getLastName())));

        return usersToBeReturned;
    }

    public UserDto signUp(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

        if(optionalUser.isPresent()) {
            throw new RuntimeException("Login already exists");
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
        user.setToken(UUID.randomUUID().toString());

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public void signOut(UserDto userDto) {
        User user = getUser(userDto.getId());

        user.setToken(null);

        userRepository.save(user);
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
}
