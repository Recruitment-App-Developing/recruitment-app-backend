package com.ducthong.TopCV.extract_data.constant;

public class ExtractDataConstant {
    public static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    public static final String API_KEY = "Bearer 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";
    public static final String MODEL = "deepseek/deepseek-chat:free";
    public static final String EXTRACT_INSTRUCTION = "{\n"
            + "  \"fullName\": \"\",\n"
            + "  \"email\": \"\",\n"
            + "  \"dateOfBirth\": \"\",\n"
            + "  \"phone\": \"\",\n"
            + "  \"address\": \"\",\n"
            + "  \"positionApply\": \"\",\n"
            + "  \"award\": [\n"
            + "    {\n"
            + "      \"time\": \"\",\n"
            + "      \"name\": \"\"\n"
            + "    }\n"
            + "  ],\n"
            + "  \"education\": [\n"
            + "    {\n"
            + "      \"school\": \"\",\n"
            + "      \"time\": \"\",\n"
            + "      \"industry\": \"\",\n"
            + "      \"description\": \"\"\n"
            + "    }\n"
            + "  ],\n"
            + "  \"experience\": [\n"
            + "    {\n"
            + "      \"company\": \"\",\n"
            + "      \"time\": \"\",\n"
            + "      \"position\": \"\",\n"
            + "      \"description\": \"\"\n"
            + "    }\n"
            + "  ],\n"
            + "  \"technicalSkills\": [],\n"
            + "  \"softSkills\": []\n"
            + "}\n"
            + "Give the extracted information in json format only (Fields without data will be left as null)";
}
