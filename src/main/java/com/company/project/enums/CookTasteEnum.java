package com.company.project.enums;

import lombok.Getter;

@Getter
public enum CookTasteEnum {

    MILD("微辣", "0"),

    MEDIUM("中辣", "1"),

    HOT("特辣", "2");


    private String label;
    private String value;

    CookTasteEnum(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static boolean validateValue(String value) {
        for (CookTasteEnum e : CookTasteEnum.values()) {
            if (e.getValue().equals(value)) {
                return true;
            }
        }

        return false;
    }

}