package com.example.boardexample.category;

import java.util.Arrays;

public enum BoardCategory {
    ALL,
    NOTICE,
    FREE,
    QUESTION,
    INFORMATION;

    public static BoardCategory formString(String category) {
        return Arrays.stream(BoardCategory.values())
                .filter(boardCategory -> boardCategory.name().equalsIgnoreCase(category))
                .findFirst()
                .orElse(ALL);
    }
}
