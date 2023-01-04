package com.bkalika.socialnetwork.mappers;

import com.bkalika.socialnetwork.dto.MessageDto;
import com.bkalika.socialnetwork.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author @bkalika
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MessageMapper {
    List<MessageDto> messagesToMessageDtos(List<Message> messages);

    @Mapping(target = "userDto", source = "user")
    MessageDto messageToMessageDto(Message message);

    Message messageDtoToMessage(MessageDto messageDto);
}
