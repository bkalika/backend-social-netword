package com.bkalika.socialnetwork.controllers;

import com.bkalika.socialnetwork.dto.ImageDto;
import com.bkalika.socialnetwork.dto.MessageDto;
import com.bkalika.socialnetwork.dto.UserDto;
import com.bkalika.socialnetwork.services.AuthenticationService;
import com.bkalika.socialnetwork.services.CommunityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

/**
 * @author @bkalika
 */
@RestController
@RequestMapping("/v1/community")
public class CommunityController {
    private final CommunityService communityService;
    private final AuthenticationService authenticationService;

    public CommunityController(CommunityService communityService,
                               AuthenticationService authenticationService) {
        this.communityService = communityService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDto>> getCommunityMessages(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        System.out.println("Principal: " + principal);
        UserDto userDto = authenticationService.findOrCreateByLogin(principal);
        return ResponseEntity.ok(communityService.getCommunityMessages(userDto, page));
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageDto>> getCommunityImages(
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(communityService.getCommunityImages(page));
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageDto> postMessage(@RequestBody MessageDto messageDto) {
        return ResponseEntity.created(URI.create("/v1/community/messages"))
                .body(communityService.postMessage(messageDto));
    }

    @PostMapping("/images")
    public ResponseEntity<ImageDto> postImage(@RequestParam MultipartFile file,
                                              @RequestParam(value = "title") String title) {
        return ResponseEntity.created(URI.create("/v1/community/images"))
                .body(communityService.postImage(file, title));
    }
}
