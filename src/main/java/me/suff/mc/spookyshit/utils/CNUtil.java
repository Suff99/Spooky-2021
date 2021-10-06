package me.suff.mc.spookyshit.utils;

import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;

import java.util.Random;

public class CNUtil {

    public static Random RANDOM = new Random();

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = RANDOM.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static boolean shouldChange(World world) {
        return world.provider instanceof WorldProviderSurface;
    }
}
