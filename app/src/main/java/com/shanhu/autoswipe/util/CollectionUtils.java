package com.shanhu.autoswipe.util;

import java.util.*;
/**
 * Description:  字符串，数组，ArrayList等相互转换(Java8)
 * <p>
 * Create:       2018/6/17 16:57
 *
 * @author Yang Meng(eyangmeng@163.com)
 */
public class CollectionUtils {

    /**
     * 字符串转换为字符串数组
     *
     * @param content 字符串
     * @return 字符串数组
     */
    public static String[] string2Array(String content) {
        if (content == null || content.length() == 0) {
            return null;
        }
        return content.split(SEPARATE);
    }

    /**
     * 字符串数组转换为字符串
     *
     * @param stringArray 字符串数组
     * @return 字符串
     */
    public static String array2String(String [] stringArray) {
        if (stringArray == null) {
            return null;
        }
        return String.join(JOIN_FLAG, stringArray);
    }

    /**
     * 字符串数组转换为字符串List
     *
     * @param stringArray 字符串数组
     * @return 字符串List
     */
    public static List<String> array2List(String [] stringArray) {
        if (stringArray == null) {
            return null;
        }
        return Arrays.asList(stringArray);
    }

    /**
     * 字符串List转换为字符串数组
     *
     * @param stringList 字符串List
     * @return 字符串数组
     */
    public static String[] list2Array(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        return stringList.toArray(new String[0]);
    }

    /**
     * 字符串List转换为字符串Set
     *
     * @param stringList 字符串List
     * @return 字符串Set
     */
    public static Set<String> list2Set(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        return new HashSet<>(stringList);
    }

    /**
     * 字符串Set转换为字符串List
     *
     * @param stringSet 字符串Set
     * @return 字符串List
     */
    public static List<String> set2List(Set<String> stringSet) {
        if (stringSet == null) {
            return null;
        }
        return new ArrayList<>(stringSet);
    }

    private final static String SEPARATE = "\n";
    private final static String JOIN_FLAG = " ";
}