package com.ducthong.TopCV.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.UUID;

@Log4j2
public class Common {
    public static String toJsonString(Object object) throws JsonProcessingException {
        ObjectMapper ob = new ObjectMapper();
        return ob.writeValueAsString(object);
    }
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
    public static String arrayToString(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    public static String[] stringToArray(String str) {
        try {
            String[] result = str.split(",");
            return Arrays.stream(result).map(String::trim).toArray(String[]::new);
        } catch (Exception e) {
            log.error("Error when split array");
            return null;
        }
    }
}
