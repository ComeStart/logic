package cn.comestart.utils;

import java.util.Random;

public class RandomTools {
    private static Random random = new Random();
    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }
 }
