package com.likelion.devroutine.user.controller;

import com.likelion.devroutine.user.dto.MyProfileResponse;
import com.likelion.devroutine.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public String getProfile(Authentication authentication,
                             Model model) {
        MyProfileResponse profile = userService.getProfile(authentication.getName());
        model.addAttribute("profile", profile);
        return "user/profile";
    }
}
