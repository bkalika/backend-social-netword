package com.bkalika.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author @bkalika
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDto {
    private UserSummaryDto userDto;
    private List<UserSummaryDto> friends;
    private List<MessageDto> messages;
    private List<ImageDto> images;
}
