package com.lmscr.phonerepair.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 密码加密工具类
 */
public class Md5Password {
    /**
     * 盐值，用于增加密码的复杂度
     */
    private static final String SALT = "youjixianxuetang-java2024";

    /**
     * 生成 MD5 加密后的密码
     *
     * @param input 原始密码
     * @return 加密后的 MD5 密码
     */
    public static String generateMD5(String input) {
        try {
            // 在原始输入前后加盐
            String saltedInput = SALT + input + SALT;

            // 创建一个 MessageDigest 实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(saltedInput.getBytes());

            // 转换成十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
