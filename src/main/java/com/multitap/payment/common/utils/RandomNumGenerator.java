package com.multitap.payment.common.utils;

import java.util.Random;

public class RandomNumGenerator {

    public static String generateRandomNum(Integer length) {
        Random r = new Random();
        String randomNumber = "";
        for (int i = 0; i < length; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }

        return randomNumber;
    }

}
