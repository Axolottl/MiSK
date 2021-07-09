package com.moderocky.misk.utils;

import org.bukkit.entity.Entity;

/**
 *
 * Cloned from SkStuff
 * Probably useless :(
 *
 * @author TheBukor
 *
 */

public interface NMSInterface {

    void clearPathfinderGoals(Entity entity);

    void removePathfinderGoal(Object entity, Class<?> goalClass, boolean isTargetSelector);

    void addPathfinderGoal(Object entity, int priority, Object goal, boolean isTargetSelector);

}
