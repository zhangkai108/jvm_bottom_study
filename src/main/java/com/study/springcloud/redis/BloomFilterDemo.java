package com.study.springcloud.redis;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器测试
 *
 * 我们定义了一个布隆过滤器，有两个重要的参数，分别是 我们预计要插入多少数据，
 * 我们所期望的误判率，误判率不能为0。 我向布隆过滤器插入了0-1000000，然后用1000000-2000000来测试误判率。
 *
 *  @param
 * @author 张凯（工号：300379）
 * @since 2020/3/23 16:24
 * @version 1.0.0
 * @copyright 南京亚信网络科技公司
 */
public class BloomFilterDemo {
    private static int size = 1000000;//预计要插入多少数据

    private static double fpp = 0.001;//期望的误判率

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

    public static void main(String[] args) {
        //插入数据
        for (int i = 0; i < 1000000; i++) {
            bloomFilter.put(i);
        }
        int count = 0;
        for (int i = 1000000; i < 2000000; i++) {
            if (bloomFilter.mightContain(i)) {
                count++;
                System.out.println(i + "误判了");
            }
        }
        System.out.println("总共的误判数:" + count);
    }
}
