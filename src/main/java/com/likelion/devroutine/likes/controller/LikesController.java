package com.likelion.devroutine.likes.controller;

import com.likelion.devroutine.likes.service.LikesService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/certification/")
public class LikesController {
    private final LikesService likesService;
    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping("/{certificationId}/likes")
    public String doLikes(@PathVariable Long certificationId, Authentication authentication) {
        String oauthId = authentication.getName();
        String result = likesService.doLikes(certificationId,oauthId);
        return result;
    }

    @GetMapping("/{certificationId}/likes")
    public Integer countLikes(@PathVariable Long certificationId) {
        Integer result = likesService.countLikes(certificationId);
        return result;
    }

    @DeleteMapping("/{certificationId}/likes/{likeId}")
    public String deleteLikes(@PathVariable Long certificationId, Authentication authentication, @PathVariable Long likeId) {
        String oauthId = authentication.getName();
        String result = likesService.deleteLikes(certificationId, oauthId, likeId);
        return result;
    }
}
