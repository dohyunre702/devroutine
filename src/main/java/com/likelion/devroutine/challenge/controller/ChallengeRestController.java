package com.likelion.devroutine.challenge.controller;

import com.likelion.devroutine.challenge.dto.*;
import com.likelion.devroutine.challenge.service.ChallengeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/challenges")
@Slf4j
public class ChallengeRestController {
    private final ChallengeService challengeService;

    public ChallengeRestController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }
    @GetMapping
    public ResponseEntity<List<ChallengeDto>> findAllChallengeList(Long challengeId,
                                                                   @RequestParam(defaultValue = "5") int size, @RequestParam(required = false) String keyword){
        List<ChallengeDto> challengeDtos;
        if(keyword==null) {
            challengeDtos = challengeService.findAllChallenge(challengeId, size);
        }else{
            challengeDtos=challengeService.findAllChallengeTitle(challengeId, size, keyword);
        }
        return ResponseEntity.ok().body(challengeDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDto> findByChallengeId(Authentication authentication, @PathVariable Long id){
        //로그인 X 이거나 참여중이지 않은 챌린지 인경우
        if(!authentication.isAuthenticated() || !challengeService.isParticipate(id, authentication.getName())){
            ChallengeDto challengeDto=challengeService.findByChallengeId(id);
            return ResponseEntity.ok().body(challengeDto);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/v1/challenges/"+String.valueOf(id)+"/participation"));
        return new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
    }
    @PostMapping
    public ResponseEntity<ChallengeCreateResponse> createChallenge(Authentication authentication, @RequestBody ChallengeCreateRequest dto){
        ChallengeCreateResponse challengeCreateResponse=challengeService.createChallenge(authentication.getName(), dto);
        return ResponseEntity.ok().body(challengeCreateResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ChallengeResponse> deleteChallenge(Authentication authentication, @PathVariable Long id){
        ChallengeResponse challengeResponse=challengeService.deleteChallenge(authentication.getName(), id);
        return ResponseEntity.ok().body(challengeResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ChallengeResponse> modifyChallenge(Authentication authentication, @PathVariable Long id, @RequestBody ChallengeModifiyRequest dto){
        ChallengeResponse challengeResponse=challengeService.modifyChallenge(authentication.getName(), id, dto);
        return ResponseEntity.ok().body(challengeResponse);
    }
}
