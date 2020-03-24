package com.study.jvm_bottom_study.javabase;

import java.util.*;

/**
 * map转list
 *
 * @author 张凯（工号：300379）
 * @since 2020/3/23 9:42
 * @version 1.0.0
 * @copyright 南京亚信网络科技公司
 */
public class Map2List {
    public static void main(String[] args) {
        //map转list
        HashMap<String, String> map = new HashMap<>();
        map.put("222","999");
        map.put("333","888");
        map.put("444","777");
        List<String> list1 = new LinkedList<>(map.keySet());
        List<String> list2 = new LinkedList<>(map.values());
        List<Map.Entry<String,String>> list3 = new LinkedList<Map.Entry<String,String>>(map.entrySet());
        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list3);
        System.out.println(map);

        //set转list
        HashSet<String> set1 = new HashSet<>(list1);
        System.out.println(set1);
    }
}
