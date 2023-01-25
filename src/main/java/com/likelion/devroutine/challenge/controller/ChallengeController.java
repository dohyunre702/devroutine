package com.likelion.devroutine.challenge.controller;

import com.likelion.devroutine.auth.domain.User;
import com.likelion.devroutine.challenge.domain.Challenge;
import com.likelion.devroutine.challenge.dto.ChallengeResponse;
import com.likelion.devroutine.challenge.service.ChallengeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/api/v1/challenges")
public class ChallengeController {
    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }
    @GetMapping
    public ResponseEntity<List<ChallengeResponse>> findAllChallengeList(Long challengeId,
                                                                        @RequestParam(defaultValue = "5") int size, @RequestParam(required = false) String keyword){
        List<ChallengeResponse> challengeResponses;
        if(keyword==null) {
            challengeResponses = challengeService.findAllChallenge(challengeId, size);
        }else{
            challengeResponses=challengeService.findAllChallengeTitle(challengeId, size, keyword);
        }
        return ResponseEntity.ok().body(challengeResponses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResponse> findByChallengeId(@PathVariable Long id){
        // 로그인 되어있는지 확인
        // 로그인된 유저가 참여중인 챌린지이면 /{chanllengeId}/challenges-detail/{id}로 리다이렉트

        //로그인 X 이거나 참여중이지 않은 챌린지 인경우
        ChallengeResponse challengeResponse=challengeService.findByChallengeId(id);
        return ResponseEntity.ok().body(challengeResponse);
    }
    @GetMapping("/register")
    public String registerForm(@ModelAttribute ChallengeForm challengeForm) {
        return "challenge/registerForm";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute ChallengeForm challengeForm, BindingResult bindingResult, @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            return "challenge/registerForm";
        }

        Long registerId = ChallengeService.register(challengeForm.getTitle(), challengeForm.getDes(), loginUser.getId());
        redirectAttributes.addAttribute("challengeId", registerId);

        return "redirect:/challenge/{challengeId}";
    }
    @GetMapping("/{challengeId}/edit")
    public String editForm(@PathVariable Long challengeId, Model model) {

        Challenge challenge = ChallengeService.register(challengeId).orElseThrow();

        ChallengeForm challengeForm = new ChallengeForm();
        challengeForm.setTitle(challenge.getTitle());
        challengeForm.setDes(challenge.getDescription());

        model.addAttribute("challengeForm", challengeForm);
        model.addAttribute("challengeId", challengeId);

        return "challenge/editForm";
    }

    @PostMapping("/{challengeId}/edit")
    public String edit(@PathVariable Long challengeId, @Valid @ModelAttribute ChallengeForm challengeForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            //로그
            return "challenge/editForm";
        }

        challengeService.updatechallenge(challengeId, challengeForm.getTitle(), challengeForm.getDes());

        return "redirect:/challenge/{challengeId}";
    }
    @PostMapping("/{challengeId}/delete")
    public String delete(@PathVariable Long challengeId) {
        ChallengeService.deleteById(challengeId);
        return "redirect:/challenge";
    }

}
