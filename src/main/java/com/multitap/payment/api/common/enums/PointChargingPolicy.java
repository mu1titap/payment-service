package com.multitap.payment.api.common.enums;

import java.util.Arrays;

public enum PointChargingPolicy {
    FIRST(10, 1000),
    SECOND(30, 3000),
    THIRD(50, 5000),
    FOURTH(100, 10000),
    FIFTH(300, 30000),
    SIXTH(500, 50000);

    private final int points;
    private final int price;

    PointChargingPolicy(int points, int price) {
        this.points = points;
        this.price = price;
    }

    int getPoints() {
        return points;
    }

    int getPrice() {
        return price;
    }


    public static int[] getAllPrices() {
        return Arrays.stream(values())
            .mapToInt(PointChargingPolicy::getPrice)
            .toArray();
    }


    public static int[] getAllPoints() {
        return Arrays.stream(values())
            .mapToInt(PointChargingPolicy::getPoints)
            .toArray();
    }


}
