package com.likelion.devroutine.likes.domain;

import com.likelion.devroutine.certification.domain.Certification;
import com.likelion.devroutine.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certification_id")
    Certification certification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    User user;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public static Likes of(User user, Certification certification) {
        Likes likes = Likes.builder()
                .user(user)
                .certification(certification)
                .build();

        return likes;
    }

}
