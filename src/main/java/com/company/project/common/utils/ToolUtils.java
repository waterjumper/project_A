package com.company.project.common.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ToolUtils {

    public static final String EMPTY = "";

    public static String projectDir() {
        return System.getProperty("user.dir");
    }


    public static long nanoTime() {
        return System.nanoTime();
    }


    public static long timestamp(){
        return LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
    }

    public static long timestamp2(){
        return System.currentTimeMillis();
    }

    public static String getFileType(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }

        return EMPTY;
    }



    public static String uniCode(String prefix, int alphanumericNum) {
        return prefix + RandomStringUtils.randomAlphanumeric(alphanumericNum) + System.currentTimeMillis()
                + RandomStringUtils.randomAlphanumeric(alphanumericNum);
    }
}
