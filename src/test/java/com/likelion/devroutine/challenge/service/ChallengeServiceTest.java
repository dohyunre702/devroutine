package com.likelion.devroutine.challenge.service;

import com.likelion.devroutine.auth.domain.User;
import com.likelion.devroutine.challenge.domain.Challenge;
import com.likelion.devroutine.challenge.dto.ChallengeResponse;
import com.likelion.devroutine.challenge.enumerate.AuthenticationType;
import com.likelion.devroutine.challenge.exception.ChallengeNotFoundException;
import com.likelion.devroutine.challenge.exception.InaccessibleChallengeException;
import com.likelion.devroutine.challenge.repository.ChallengeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ChallengeServiceTest {
    private ChallengeService challengeService;
    private ChallengeRepository challengeRepository=mock(ChallengeRepository.class);
    private EntityManager entityManager;
    private final Challenge CHALLENGE=Challenge.builder()
            .id(1l)
            .title("1일 1알고리즘")
            .description("하루에 알고리즘 한문제 이상 풀기")
            .vigibility(true)
            .authenticationType(AuthenticationType.PICTURE)
            .fromUserId(1l)
            .build();
    @BeforeEach
    void setup(){
        challengeService=new ChallengeService(challengeRepository);
    }
    @Test
    @DisplayName("챌린지 상세 조회 성공")
    void findByChallengeId_success(){
        Mockito.when(challengeRepository.findById(CHALLENGE.getId())).thenReturn(Optional.of(CHALLENGE));

        ChallengeResponse challengeResponse=challengeService.findByChallengeId(CHALLENGE.getId());

        assertEquals(challengeResponse.getTitle(), CHALLENGE.getTitle());
        assertEquals(challengeResponse.getAuthenticationType(), CHALLENGE.getAuthenticationType());
    }
    @Test
    @DisplayName("챌린지 상세 조회 실패 - 챌린지 존재하지 않을 때")
    void findByChallengeId_fail1(){
        Mockito.when(challengeRepository.findById(CHALLENGE.getId())).thenThrow(new ChallengeNotFoundException());

        assertThrows(ChallengeNotFoundException.class, ()->challengeService.findByChallengeId(CHALLENGE.getId()));
    }
    @Test
    @DisplayName("챌린지 상세 조회 실패 - 비공개 챌린지 접근할 때 ")
    void findByChallengeId_fail2(){
        Mockito.when(challengeRepository.findById(CHALLENGE.getId())).thenThrow(new InaccessibleChallengeException());

        assertThrows(InaccessibleChallengeException.class, ()->challengeService.findByChallengeId(CHALLENGE.getId()));
    }

    @Test
    @DisplayName("챌린지 생성")
    public void register() {
        //given
        User user = User.builder().id(1l).build();
        entityManager.persist(user);

        //when
        Long registerId = challengeService.register("제목", "내용", user.getId());

        //then
        Challenge challenge = challengeRepository.findById(registerId).orElseThrow();

        assertThat(challenge.getTitle()).isEqualTo("제목");
        assertThat(challenge.getDescription()).isEqualTo("내용");
        assertThat(challenge.getId()).isEqualTo(user);
    }

    @Test
    @DisplayName("챌린지 수정/삭제")
    public void updateChallenge() {
        //given
        User user = User.builder().id(1L).build();
        entityManager.persist(user);
        Long registerId = challengeService.register("제목", "내용", user.getId());

        //when
        challengeService.updatechallenge(registerId, "수정 제목", "수정 내용");

        //then
        Challenge challenge = challengeRepository.findById(registerId).orElseThrow();

        assertThat(challenge.getTitle()).isEqualTo("수정 제목");
        assertThat(challenge.getDescription()).isEqualTo("수정 내용");
        assertThat(challenge.getId()).isEqualTo(user);
    }
}