package com.company.project.enums;

import lombok.Getter;

@Getter
public enum CookIngredientEnum {

    GREENONIONS("葱", "0"),

    GINGER("姜", "1"),

    CORIANDER("香菜", "2"),

    NOT_SELECT("不放", "-1")


    ;


    private String label;
    private String value;

    CookIngredientEnum(String label, String value) {
        this.label = label;
        this.value = value;
    }


    public static boolean validateValue(String value) {
        for (CookIngredientEnum e : CookIngredientEnum.values()) {
            if (e.getValue().equals(value)) {
                return true;
            }
        }

        return false;
    }

}