package com.lth.cookingassistant.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;


public enum EDayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THUSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    private static final Map<String, EDayOfWeek> namesMap = new HashMap<String, EDayOfWeek>(7);

    static {
        namesMap.put("monday", MONDAY);
        namesMap.put("tuesday", TUESDAY);
        namesMap.put("wednesday", WEDNESDAY);
        namesMap.put("thusday", THUSDAY);
        namesMap.put("friday", FRIDAY);
        namesMap.put("saturday", SATURDAY);
        namesMap.put("sunday", SUNDAY);
    }

    @JsonCreator
    public static EDayOfWeek forValue(String value) {
        return namesMap.get(StringUtils.lowerCase(value));
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, EDayOfWeek> entry : namesMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null; // or fail
    }
}
