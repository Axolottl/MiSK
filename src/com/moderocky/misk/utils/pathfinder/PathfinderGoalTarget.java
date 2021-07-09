package com.moderocky.misk.utils.pathfinder;

import net.minecraft.server.v1_14_R1.EntityInsentient;
import net.minecraft.server.v1_14_R1.EntityLiving;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class PathfinderGoalTarget {

    public static void targetEntity(EntityLiving entity, EntityLiving target) {
        EntityInsentient insentient = (EntityInsentient) entity;
        if (insentient != null) {
            insentient.setGoalTarget(target);
        }
    }

    public static void targetEntity(Entity entity, Entity target) {
        EntityInsentient insentient = (EntityInsentient) ((CraftEntity) entity).getHandle();
        EntityLiving living = (EntityLiving) ((CraftEntity) target).getHandle();
        if (insentient != null && living != null) {
            insentient.setGoalTarget(living);
        }
    }
}
