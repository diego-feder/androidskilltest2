package diegofeder.androidskilltest2a.utils;

import java.util.UUID;

public class GuidGenerator {
    public static String generate() {
        String uuid;
        return uuid = UUID.randomUUID().toString().replace("-", "");
    }

}
