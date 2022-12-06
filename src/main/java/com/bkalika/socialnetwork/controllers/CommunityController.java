package com.bkalika.socialnetwork.controllers;

import com.bkalika.socialnetwork.dto.ImageDto;
import com.bkalika.socialnetwork.dto.MessageDto;
import com.bkalika.socialnetwork.dto.UserDto;
import com.bkalika.socialnetwork.services.CommunityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDto>> getCommunityMessages(
            @AuthenticationPrincipal UserDto userDto,
            @RequestParam(value = "nextPage", defaultValue = "false") boolean nextPage,
            HttpSession session
    ) {
        Integer currentPage = (Integer) session.getAttribute("currentPageMessages");
        if(currentPage == null) {
            currentPage = 0;
        }

        if(nextPage) {
            currentPage++;
        }
        session.setAttribute("currentPageMessages", currentPage);

        return ResponseEntity.ok(communityService.getCommunityMessages(userDto, currentPage));
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageDto>> getCommunityImages(
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(communityService.getCommunityImages(page));
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageDto> postMessage(
            @AuthenticationPrincipal UserDto userDto,
            @RequestBody MessageDto messageDto) {

        return ResponseEntity.created(URI.create("/v1/community/messages"))
                .body(communityService.postMessage(userDto, messageDto));
    }

    @PostMapping("/images")
    public ResponseEntity<ImageDto> postImage(@RequestParam MultipartFile file,
                                              @RequestParam(value = "title") String title) {
        return ResponseEntity.created(URI.create("/v1/community/images"))
                .body(communityService.postImage(file, title));
    }
}
