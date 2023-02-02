package com.likelion.devroutine.likes.service;

import com.likelion.devroutine.certification.domain.Certification;
import com.likelion.devroutine.likes.domain.Likes;
import com.likelion.devroutine.likes.exception.CertificationNotFoundException;
import com.likelion.devroutine.likes.exception.DuplicatedLikesException;
import com.likelion.devroutine.likes.exception.InaccessibleException;
import com.likelion.devroutine.likes.repository.LikesRepository;
import com.likelion.devroutine.user.domain.User;
import com.likelion.devroutine.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;

    public String doLikes(Long certificationId, String oauthId) {
        User user = getUser(oauthId);
        Certification certification = getCertification(certificationId);
        match(certification, user);

        Likes likes = Likes.of(user, certification);
        likesRepository.save(likes);
        return "좋아요를 눌렀습니다";
    }

    public Integer countLikes(Long certificationId) {
        Certification certification = certificationRepository.findById(certificationId)
                .orElseThrow(() -> new CertificationNotFoundException());
        return likesRepository.countBy(certification);
    }

    public String deleteLikes(Long certificationId, String oauthId, Long likeId) {
        User user = getUser(oauthId);
        Certification certification = getCertification(certificationId);
        match(certification, user);

        likesRepository.deleteById(likeId);
        return "좋아요가 취소되었습니다";
    }

    public User getUser(String oauthId) {
        User user = userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new InaccessibleException());
        return user;
    }

    public Certification getCertification(Long id) {
        Certification checkCertification = certificationRepository.findById(id)
                .orElseThrow(() -> new CertificationNotFoundException());
        return checkCertification;
    }

    public boolean match(Certification certification, User user) {
        //챌린지에 참여하는 사람만 할 수 있게 한다. (?)
        //로그인하는 사람 모두가 할 수 있다. (v)
        //회원가입하지 않아도 모두가 할 수 있다. (x)
        if (!user.getId().equals(certification.getParticipation().getUser().getId())) {
            throw new DuplicatedLikesException();
        }
        return true;
    }
}
