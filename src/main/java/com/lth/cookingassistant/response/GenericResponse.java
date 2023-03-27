package com.lth.cookingassistant.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GenericResponse {
    private String message;
    private String error;
}
