package com.moderocky.misk.utils;


import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 *
 * This came from SkStuff. I mean, it's literally the same as the Joshua Edwards one, but I thought I'd use this.
 *
 * @author TheBukor
 *
 *
 */

public class ReflectionUtils {

    public static Class<?> getNMSClass(String classString) {
        String version = getVersion();
        String name = "net.minecraft.server." + version + classString;
        Class<?> nmsClass = null;
        try {
            nmsClass = Class.forName(name);
        } catch (ClassNotFoundException ex) {
            Bukkit.getLogger().warning("Unable to get NMS class \'" + name + "\'! You are probably running an unsupported version!");
            return null;
        }
        return nmsClass;
    }

    public static Class<?> getOBCClass(String classString) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + ".";
        String name = "org.bukkit.craftbukkit." + version + classString;
        Class<?> obcClass = null;
        try {
            obcClass = Class.forName(name);
        } catch (ClassNotFoundException ex) {
            Bukkit.getLogger().warning("Unable to get OBC class \'" + name + "\'! You are probably running an unsupported version!");
            return null;
        }
        return obcClass;
    }

    public static Object getField(String field, Class<?> clazz, Object object) {
        Field f = null;
        Object obj = null;
        try {
            f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            obj = f.get(object);
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public static void setField(String field, Class<?> clazz, Object object, Object toSet) {
        Field f = null;
        try {
            f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            f.set(object, toSet);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }

    public static Constructor<?> getConstructor(Class<?> clazz, Class<?> ... params) {
        Constructor<?> constr = null;
        try {
            constr = clazz.getDeclaredConstructor(params);
            constr.setAccessible(true);
        } catch (NoSuchMethodException | SecurityException ex) {
            ex.printStackTrace();
        }
        return constr;
    }

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + ".";
    }
}
