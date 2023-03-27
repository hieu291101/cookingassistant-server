package com.lth.cookingassistant.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private String accessToken;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
