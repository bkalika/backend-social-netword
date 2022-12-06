package com.bkalika.socialnetwork.services;

import com.bkalika.socialnetwork.entities.User;

import com.bkalika.socialnetwork.dto.ImageDto;
import com.bkalika.socialnetwork.dto.MessageDto;
import com.bkalika.socialnetwork.dto.UserDto;
import com.bkalika.socialnetwork.entities.Message;
import com.bkalika.socialnetwork.repositories.MessageRepository;
import com.bkalika.socialnetwork.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author @bkalika
 */
@Service
public class CommunityService {
    private static final int PAGE_SIZE = 10;

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public CommunityService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<MessageDto> getCommunityMessages(UserDto userDto, int page) {
        User user = getUser(userDto);

        List<Long> friendIds = Optional.of(user.getFriends())
                .map(friends -> friends.stream().map(User::getId).collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        List<Message> messages = messageRepository.findCommunityMessages(friendIds, PageRequest.of(page, PAGE_SIZE));

        List<MessageDto> messageDtos = new ArrayList<>();
        messages.forEach(message -> messageDtos.add(new MessageDto(message.getId(), message.getContent())));

        return messageDtos;
    }

    public List<ImageDto> getCommunityImages(int page) {
        return Arrays.asList(new ImageDto(1L, "First title", null),
                new ImageDto(2L, "Second title", null));
    }

    public MessageDto postMessage(UserDto userDto, MessageDto messageDto) {
        User user = getUser(userDto);

        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setUser(user);

        if(user.getMessages() == null)
            user.setMessages(new ArrayList<>());
        user.getMessages().add(message);

        Message savedMessage = messageRepository.save(message);
        return new MessageDto(savedMessage.getId(), savedMessage.getContent());
    }

    public ImageDto postImage(MultipartFile file, String title) {
        return new ImageDto(3L, "new title", null);
    }

    private User getUser(UserDto userDto) {
        return userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
