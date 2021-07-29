package io.ryan.platform.demo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Demo
 *
 * @author hkc
 * @version 1.0.0
 * @date 2021-07-29 11:54
 */
public class Demo {

    public static void main(String[] args) throws Exception {

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime expiration = localDateTime.plus(1, ChronoUnit.SECONDS);
        Thread.sleep(1000);
        LocalDateTime now = LocalDateTime.now();

        System.out.println(localDateTime);
        System.out.println(expiration);
        System.out.println(now);
        if (now.isAfter(expiration)) {
            System.out.println("时间超时了");
        }
    }
}
