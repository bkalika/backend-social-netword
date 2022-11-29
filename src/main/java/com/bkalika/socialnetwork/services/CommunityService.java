package com.bkalika.socialnetwork.services;

import com.bkalika.socialnetwork.dto.ImageDto;
import com.bkalika.socialnetwork.dto.MessageDto;
import com.bkalika.socialnetwork.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * @author @bkalika
 */
@Service
public class CommunityService {
    public List<MessageDto> getCommunityMessages(UserDto userDto, int page) {
        return Arrays.asList(new MessageDto(1L, "First message" + userDto.getFirstName()),
                new MessageDto(2L, "Second message" + userDto.getFirstName()));
    }

    public List<ImageDto> getCommunityImages(int page) {
        return Arrays.asList(new ImageDto(1L, "Firlst title", null),
                new ImageDto(2L, "Second title", null));
    }

    public MessageDto postMessage(MessageDto messageDto) {
        return new MessageDto(3L, "new message");
    }

    public ImageDto postImage(MultipartFile file, String title) {
        return new ImageDto(3L, "new title", null);
    }
}
