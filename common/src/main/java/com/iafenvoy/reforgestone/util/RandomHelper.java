package com.iafenvoy.reforgestone.util;

import java.util.List;
import java.util.Random;

public class RandomHelper {
    public static <T> T randomOne(Random random, List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}

