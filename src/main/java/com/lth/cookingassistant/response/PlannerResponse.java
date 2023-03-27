package com.lth.cookingassistant.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lth.cookingassistant.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class PlannerResponse {
    private long plannerId;
    private EDayOfWeek dayOfWeek;
    private byte isDelete;
    private EMeal meal;
    private String mealDesciption;
    private Recipe recipe;
    private Long mealOfDayId;
}
