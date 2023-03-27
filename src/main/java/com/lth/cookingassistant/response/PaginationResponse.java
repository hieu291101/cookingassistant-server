package com.lth.cookingassistant.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PaginationResponse {
    private int page;
    private int limit;
    private long totalRows;
}
