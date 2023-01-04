package com.bkalika.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author @bkalika
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialsDto {
    private String login;
    private char[] password;
}
