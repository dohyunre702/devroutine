package com.likelion.devroutine.likes.exception;

import com.likelion.devroutine.exception.UnauthorizedException;

public class InaccessibleException extends UnauthorizedException {
    private static final String MESSAGE = "로그인이 필요합니다.";

    public InaccessibleException() {
        super(MESSAGE);
    }
}
