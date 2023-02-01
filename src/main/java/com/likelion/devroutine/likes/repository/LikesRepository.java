package com.likelion.devroutine.likes.repository;

import com.likelion.devroutine.certification.domain.Certification;
import com.likelion.devroutine.likes.domain.Likes;
import com.likelion.devroutine.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByCertificationAndUser(Certification certification, User user);

    Integer countBy(Certification certification);

}
