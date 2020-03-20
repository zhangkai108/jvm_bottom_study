package com.study.jvm_bottom_study.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSV2OracleUtils {



    public static void main(String[] args) throws IOException {
        CSV2oracle("D:/asiaword/汇聚/13816883179/DHMP-ESB-HGUINFO-2020031115.csv","tr_user_sn_info",3);
    /*String[] arr = {"qq","we","fsd","fds"};
    String[] arr1 = new String[3];
        System.arraycopy(arr,0,arr1,0,3);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(arr1));

        String[] arr1 = new String[3];
    String str = "F430B9AD5735";
        arr1[2] = str;
        formatMAC(arr1,3);
        System.out.println(Arrays.toString(arr1));*/
    }

/**
 *
 *
 * @param filePath 需要导入的csv文件路径
 * @param tableName 需要插入的的表名
 * @param length 取前几个插入数据库
 * @author 张凯（工号：300379）
 * @since 2020/1/2 20:22
 * @version 1.0.0
 * @copyright 南京亚信网络科技公司
 */
    public static void CSV2oracle(String filePath ,String tableName , int length )  {
        List<String> list = new ArrayList<>();
        try {
            Files.lines(Paths.get(filePath))
                    .forEach(e -> {
                        String[] strs1 = e.split(",");
                        String[] strs = new String[3];
                        System.arraycopy(strs1,0,strs,0,length);
                        formatMAC(strs,3);
                        String prefix = "insert into " + tableName + " values('";
                        String suffix = "');";
                        String splitstr = "','";
                        String resultstr = Arrays.stream(strs).collect(Collectors.joining(splitstr, prefix, suffix));
                        list.add(resultstr);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        //list.forEach(e-> System.out.println(e));
        String writeFilePath = filePath.replace(".csv","IntoOracle.sql");
        writeFileContext(list,writeFilePath);
    }
    /**
     * 将MAC地址标准化F430B9AD5735 -> f4:30:b9:ad:57:35
     * @param arr 需要数组
     * @param index 需要处理的数据在第几列
     * @throws Exception
     */
    public static void formatMAC(String[] arr,int index)  {
        index--;
        if(arr[index]!=null && arr[index].length()>0){
            char[] chars = arr[index].toLowerCase().toCharArray();
            String str = ""+chars[0] + chars[1]+":"+ chars[2] + chars[3]+":"
            + chars[4] + chars[5]+":" + chars[6] + chars[7];
            arr[index] = str;
        }

    }
    /**
     * 将list按行写入到txt文件中
     * @param strings
     * @param path
     * @throws Exception
     */
    public static void writeFileContext(List<String>  strings, String path)  {
        File file = new File(path);
        //如果没有文件就创建
        try {
            if (!file.isFile()) {
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (String l:strings){
                writer.write(l + "\r\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
