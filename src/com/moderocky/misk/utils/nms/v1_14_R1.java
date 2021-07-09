package com.moderocky.misk.utils.nms;

import com.moderocky.misk.utils.NMSInterface;
import com.moderocky.misk.utils.ReflectionUtils;
import net.minecraft.server.v1_14_R1.EntityInsentient;
import net.minecraft.server.v1_14_R1.PathfinderGoal;
import net.minecraft.server.v1_14_R1.PathfinderGoalSelector;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 *
 * Cloned from SkStuff. Untested. For future use.
 *
 * @author TheBukor
 *
 */
public class v1_14_R1 implements NMSInterface {

    public void clearPathfinderGoals(Entity entity) {
        EntityInsentient nmsEnt = (EntityInsentient) ((CraftEntity) entity).getHandle();
        ((LinkedHashSet<?>) ReflectionUtils.getField("b", PathfinderGoalSelector.class, nmsEnt.goalSelector)).clear();
        ((LinkedHashSet<?>) ReflectionUtils.getField("c", PathfinderGoalSelector.class, nmsEnt.goalSelector)).clear();
        ((LinkedHashSet<?>) ReflectionUtils.getField("b", PathfinderGoalSelector.class, nmsEnt.targetSelector)).clear();
        ((LinkedHashSet<?>) ReflectionUtils.getField("c", PathfinderGoalSelector.class, nmsEnt.targetSelector)).clear();
    }

    @Override
    public void removePathfinderGoal(Object entity, Class<?> goalClass, boolean isTargetSelector) {
        if (entity instanceof EntityInsentient) {
            ((EntityInsentient) entity).setGoalTarget(null);
            if (isTargetSelector) {
                Iterator<?> goals = ((LinkedHashSet<?>) ReflectionUtils.getField("b", PathfinderGoalSelector.class, ((EntityInsentient) entity).targetSelector)).iterator();
                while (goals.hasNext()) {
                    Object goal = goals.next();
                    if (ReflectionUtils.getField("a", goal.getClass(), goal).getClass() == goalClass) {
                        goals.remove();
                    }
                }
            } else {
                Iterator<?> goals = ((LinkedHashSet<?>) ReflectionUtils.getField("b", PathfinderGoalSelector.class, ((EntityInsentient) entity).goalSelector)).iterator();
                while (goals.hasNext()) {
                    Object goal = goals.next();
                    if (ReflectionUtils.getField("a", goal.getClass(), goal).getClass() == goalClass) {
                        goals.remove();
                    }
                }
            }
        }
    }

    @Override
    public void addPathfinderGoal(Object entity, int priority, Object goal, boolean isTargetSelector) {
        if (entity instanceof EntityInsentient && goal instanceof PathfinderGoal) {
            if (isTargetSelector)
                ((EntityInsentient) entity).targetSelector.a(priority, (PathfinderGoal) goal);
            else
                ((EntityInsentient) entity).goalSelector.a(priority, (PathfinderGoal) goal);
        }
    }
}
