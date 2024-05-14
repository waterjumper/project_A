package com.company.project.enums;

import lombok.Getter;

@Getter
public enum CookSizeEnum {

    SMALL("小份", "0"),

    MEDIUM("中份", "1"),

    LARGE("大份", "2");


    private String label;
    private String value;

    CookSizeEnum(String label, String value) {
        this.label = label;
        this.value = value;
    }


    public static boolean validateValue(String value) {
        for (CookSizeEnum e : CookSizeEnum.values()) {
            if (e.getValue().equals(value)) {
                return true;
            }
        }

        return false;
    }

}