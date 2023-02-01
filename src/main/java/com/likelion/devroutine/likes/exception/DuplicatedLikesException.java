package com.likelion.devroutine.likes.exception;

import com.likelion.devroutine.exception.ConflictException;

public class DuplicatedLikesException extends ConflictException {
    private static final String MESSAGE = "이미 좋아요를 눌렀습니다.";

    public DuplicatedLikesException() {
        super(MESSAGE);
    }
}
