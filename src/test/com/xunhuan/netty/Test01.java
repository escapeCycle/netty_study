package com.xunhuan.netty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Auther: tianhuan
 * @Date: 2019/5/15 11:04
 * @Description:
 */
public class Test01 {

    public static void main(String[] args) {

//        try {
//            C c = new C();
//            c.add();
//        }finally {
//            System.out.println("test");
//        }

        BigDecimal decimal = new BigDecimal("200.00");
        System.out.println(decimal);

        BigDecimal decimal2 = new BigDecimal(200.00);
        System.out.println(decimal2);

        BigDecimal decimal3 = new BigDecimal(200.1);
        System.out.println(decimal3.multiply(new BigDecimal("10")));
        System.out.println(decimal3);

        BigDecimal decimal4 = new BigDecimal("200.1");
        System.out.println(decimal4);

        BigDecimal bigDecimal = BigDecimal.valueOf(200);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(200.00);
        System.out.println(bigDecimal);
        System.out.println(bigDecimal2.toString());

        System.out.println("------------------------");


        System.out.println(BigDecimal.valueOf(200.02));
    }

    public void test(){

    }
}
