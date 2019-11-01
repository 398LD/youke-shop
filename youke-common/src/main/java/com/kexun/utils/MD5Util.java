package com.kexun.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    private static final String SALT = "youkeshop";

    public static String entry(String code) {

        return DigestUtils.md5Hex(code + SALT);

    }

    public static void main(String[] args) {
        String cph39sy8LD = DigestUtils.md5Hex("123456");
        String s = cph39sy8LD.toUpperCase();
        System.out.println(s);
    }


}
