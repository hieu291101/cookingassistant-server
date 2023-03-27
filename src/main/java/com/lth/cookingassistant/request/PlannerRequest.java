package com.lth.cookingassistant.request;

import com.lth.cookingassistant.model.EDayOfWeek;
import com.lth.cookingassistant.model.EMeal;
import lombok.Data;

@Data
public class PlannerRequest {
    private Long accountId;
    private Long recipeId;
    private EDayOfWeek dayOfWeek;
    private EMeal meal;
}
