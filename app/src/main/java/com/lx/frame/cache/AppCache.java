package com.lx.frame.cache;

import com.tencent.mmkv.MMKV;

import java.util.Set;

public class AppCache {

    private static String TEST_INT = "TEST_INT";
    private static String TEST_STRING = "TEST_STRING";
    private static String TEST_SET_STRING = "TEST_SET_STRING";

    /**
     * 存储 获取 int类型
     */
    public static void setInt(int test) {
        MMKV.defaultMMKV().encode(TEST_INT, test);
    }

    public static int getInt() {
        return MMKV.defaultMMKV().decodeInt(TEST_INT);
    }

    /**
     * 存储 获取 string类型
     */
    public static void setString(int test) {
        MMKV.defaultMMKV().encode(TEST_STRING, test);
    }

    public static String getString() {
        return MMKV.defaultMMKV().decodeString(TEST_STRING);
    }

    /**
     * 存储 获取 Set<string>类型
     */
    public static void setSetString(Set<String> test) {
        MMKV.defaultMMKV().encode(TEST_SET_STRING, test);
    }

    public static Set<String> getSetString() {
        return MMKV.defaultMMKV().decodeStringSet(TEST_SET_STRING);
    }
}
