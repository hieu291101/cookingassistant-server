package com.lth.cookingassistant.response;

import java.util.List;
import com.lth.cookingassistant.model.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PlannerMealResponse {
    private long plannerId;
    private EDayOfWeek dayOfWeek;
    private byte isDelete;
    private EMeal meal;
    private String mealDesciption;
    private Recipe recipe;
    private Long mealOfDayId;
}
