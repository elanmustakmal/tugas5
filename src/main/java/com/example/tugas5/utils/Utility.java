package com.example.tugas5.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Utility {

    public static Map objectToMap(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(object,Map.class);
        return map;
    }

}
