package com.lth.cookingassistant.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ForgotPasswordRequest {
    @NotBlank
    private String forgotPasswordEmail;

    // constructors / getters and setters
}
