package com.likelion.devroutine.challenge.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ChallengeForm {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotNull
    private String des;

}
