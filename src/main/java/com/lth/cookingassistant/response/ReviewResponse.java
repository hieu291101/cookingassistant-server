package com.lth.cookingassistant.response;

import java.util.Date;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ReviewResponse {
    private Long recipeReviewId;
    private String reviewText;
    private String photoPath;
    private String name;
    private Date createdDate;
}
