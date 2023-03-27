package com.lth.cookingassistant.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum EMeal {
    BREAKFAST,
    LUNCH,
    DINNER;

    private static final Map<String, EMeal> namesMap = new HashMap<String, EMeal>(3);

    static {
        namesMap.put("breakfast", BREAKFAST);
        namesMap.put("lunch", LUNCH);
        namesMap.put("dinner", DINNER);
    }

    @JsonCreator
    public static EMeal forValue(String value) {
        return namesMap.get(StringUtils.lowerCase(value));
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, EMeal> entry : namesMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null; // or fail
    }

}
