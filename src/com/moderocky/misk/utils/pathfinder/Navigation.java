package com.moderocky.misk.utils.pathfinder;

import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Location;

import java.lang.reflect.Method;

public class Navigation {
    public static NavigationAbstract getNavigation(Entity nmsEntity) throws Exception
    {
        if (nmsEntity instanceof EntityCreature) {
            Method method = EntityInsentient.class.getDeclaredMethod("getNavigation");
            method.setAccessible(true);
            return (NavigationAbstract) method.invoke(nmsEntity);
        } else {
            return null;
        }
    }
    public static void setPathTarget(NavigationAbstract navigationAbstract, Location location, Double speed) throws Exception
    {
        PathEntity pathEntity = navigationAbstract.calculateDestination(location.getX(), location.getY(), location.getZ());
        navigationAbstract.a(pathEntity, speed);
    }
    public static void setPathTarget(NavigationAbstract navigationAbstract, Entity target, Double speed) throws Exception
    {
        PathEntity pathEntity = navigationAbstract.calculateDestination(target);
        navigationAbstract.a(pathEntity, speed);
    }
    public static void moveToPosition(Entity nmsEntity, Location location, Double speed) {
        try {
            NavigationAbstract path = Navigation.getNavigation(nmsEntity);
            Navigation.setPathTarget(path, location, speed);
        } catch (Exception exception) {
            // Bye bye :)
        }
    }
    public static void moveToPosition(Entity nmsEntity, Entity target, Double speed) {
        try {
            NavigationAbstract path = Navigation.getNavigation(nmsEntity);
            Navigation.setPathTarget(path, target, speed);
        } catch (Exception exception) {
            // Bye bye :)
        }
    }
    public static void moveToPosition(EntityInsentient entityInsentient, Location location, Double speed) {
        PathEntity pathEntity = entityInsentient.getNavigation().a(location.getX(), location.getY(), location.getZ());
        entityInsentient.getNavigation().a(pathEntity, speed);
    }

    public static void stopPathfinding(Entity nmsEntity) {
        EntityInsentient entityInsentient = (EntityInsentient) nmsEntity;
        if (entityInsentient != null) {
            entityInsentient.getNavigation().stopPathfinding();
        }
    }

}
