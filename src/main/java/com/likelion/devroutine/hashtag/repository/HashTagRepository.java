package com.likelion.devroutine.hashtag.repository;

import com.likelion.devroutine.hashtag.domain.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    Optional<HashTag> findByContents(String hashTagContents);
}
