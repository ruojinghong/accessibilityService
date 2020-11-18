package com.demacia.garen.utils;

public class RandomHelper {
    public static char RandLowerChar() {
        return (char) randomInt(97, 123);
    }

    public static char RandNumber() {
        return (char) randomInt(48, 58);
    }

    public static String RandNumberString(int i) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < i) {
            sb.append(RandNumber());
        }
        return sb.toString();
    }

    public static String RandNumberStringWithout4(int i) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < i) {
            char RandNumber = RandNumber();
            if (RandNumber != '4') {
                sb.append(RandNumber);
            }
        }
        return sb.toString();
    }

    public static String RandString(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            int randomInt = randomInt(3);
            if (randomInt == 0) {
                sb.append(RandNumber());
            } else if (randomInt == 1) {
                sb.append(RandLowerChar());
            } else if (randomInt == 2) {
                sb.append(RandUpperChar());
            }
        }
        return sb.toString();
    }

    public static String RandString(int i, int i2) {
        return RandString(randomInt(i, i2));
    }

    public static char RandUpperChar() {
        return (char) randomInt(65, 91);
    }

    public static int randomInt(int i) {
        return (int) (Math.random() * ((double) i));
    }

    public static int randomInt(int i, int i2) {
        return randomInt(i2 - i) + i;
    }
}
