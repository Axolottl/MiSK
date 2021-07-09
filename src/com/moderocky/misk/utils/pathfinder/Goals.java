package com.moderocky.misk.utils.pathfinder;

import com.moderocky.misk.utils.EntityUtils;
import com.moderocky.misk.utils.nms.PathfinderGoalPanicAll;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.entity.Entity;

public class Goals {
    public static void addGoalFloat(Entity entity) {
        EntityInsentient entityInsentient = EntityUtils.entityInsentient(entity);
        if (entityInsentient != null) entityInsentient.goalSelector.a(new PathfinderGoalFloat(entityInsentient));
    }
    public static void addGoalFollow(Entity entity, Entity target, Double d0, Float f0, Float f1) {
        EntityInsentient entityInsentient = EntityUtils.entityInsentient(entity);
        if (entityInsentient != null && EntityUtils.entityInsentient(target) != null)
            entityInsentient.goalSelector.a(new PathfinderGoalFollowEntity(EntityUtils.entityInsentient(target), d0, f0, f1));
    }
    public static void addGoalBreed(Entity entity, Entity target, Double d0) {
        EntityInsentient entityInsentient = EntityUtils.entityInsentient(entity);
        if (entityInsentient != null && EntityUtils.entityAnimal(target) != null)
            entityInsentient.goalSelector.a(new PathfinderGoalBreed(EntityUtils.entityAnimal(target), d0));
    }
    public static void addGoalOpenDoor(Entity entity) {
        EntityInsentient entityInsentient = EntityUtils.entityInsentient(entity);
        if (entityInsentient != null)
            entityInsentient.goalSelector.a(new PathfinderGoalDoorOpen(entityInsentient, true));
    }
    public static void addGoalBreakDoor(Entity entity) {
        EntityInsentient entityInsentient = EntityUtils.entityInsentient(entity);
        if (entityInsentient != null)
            entityInsentient.goalSelector.a(new PathfinderGoalBreakDoor(entityInsentient, (enumdifficulty) -> enumdifficulty == entityInsentient.getWorld().getDifficulty()));
    }
    public static void addGoalStrollVillage(Entity entity, Double d0) {
        EntityCreature entityCreature = EntityUtils.entityCreature(entity);
        if (entityCreature != null)
            entityCreature.goalSelector.a(new PathfinderGoalStrollVillage(entityCreature, d0));
    }
    public static void addGoalRandomStroll(Entity entity) {
        EntityCreature entityCreature = EntityUtils.entityCreature(entity);
        if (entityCreature != null) entityCreature.goalSelector.a(new PathfinderGoalRandomStroll(entityCreature, 1));
    }
    public static void addGoalPanic(Entity entity) {
        EntityInsentient entityInsentient = EntityUtils.entityInsentient(entity);
        if (entityInsentient != null)
            entityInsentient.goalSelector.a(new PathfinderGoalPanicAll(entityInsentient, 1));
    }
}
