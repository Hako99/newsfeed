package com.sparta.team2newsfeed.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;

    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String password;

    private String email;

    private String intro;

}