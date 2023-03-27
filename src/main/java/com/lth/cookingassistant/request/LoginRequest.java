package com.lth.cookingassistant.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String password;

    @NotBlank
    private String username;
}
