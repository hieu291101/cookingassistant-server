package com.lth.cookingassistant.service;

import com.lth.cookingassistant.model.Measure;

import java.util.Collection;

public interface MeasureService {
    Collection<Measure> list(int limit);
    Collection<Measure> searchMeasure(String keyword);
    Collection<Measure> list(int limit, int page);
    Measure get(int id);
    long countMeasure();
    Measure update(Measure measure);
    Measure create(Measure measure);
    Boolean delete(int id);
}
