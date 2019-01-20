package diegofeder.testskill.utils;

import java.util.UUID;

public class GuidGenerator {
    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
