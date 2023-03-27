package com.lth.cookingassistant.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lth.cookingassistant.model.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @JsonIgnore
    private Set<String> role;
}
