package com.lth.cookingassistant.service;

import java.io.IOException;

public interface ReportService {
    void generateExcel(long account_id) throws IOException;
}
