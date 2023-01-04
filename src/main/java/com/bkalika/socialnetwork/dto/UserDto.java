package com.bkalika.socialnetwork.dto;

import com.bkalika.socialnetwork.entities.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author @bkalika
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String token;
    private UserStatus status;
    private int age;
}
