package com.lth.cookingassistant.request;

import com.lth.cookingassistant.model.EDayOfWeek;
import com.lth.cookingassistant.model.EMeal;

import lombok.Data;

@Data
public class PlannerMealRequest {
    private Long accountId;
    private EDayOfWeek dayOfWeek;
    private EMeal meal;
}
