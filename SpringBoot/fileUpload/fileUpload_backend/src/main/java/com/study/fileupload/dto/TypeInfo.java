package com.study.fileupload.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeInfo {
    Folder("folder"),
    File("file");

    private final String value;

    TypeInfo(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TypeInfo fromValue(String value) {
        for (TypeInfo t: TypeInfo.values()) {
            if(t.value.equals(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
