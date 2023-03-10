package com.likelion.devroutine.participant.repository;

import com.likelion.devroutine.challenge.domain.Challenge;
import com.likelion.devroutine.participant.domain.Participation;
import com.likelion.devroutine.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Optional<Participation> findByUserAndChallenge(User user, Challenge challenge);
    List<Participation> findAllByChallenge(Challenge challenge);
    void deleteAllByChallenge(Challenge challenge);

    List<Participation> findAllByUserId(Long userId);
}
