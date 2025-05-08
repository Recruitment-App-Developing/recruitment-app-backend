package com.ducthong.TopCV.utility;

import java.util.List;

public class ListUtils {
    public static String listToString(List<String> list) {
        return String.join(",", list);
    }
    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}
