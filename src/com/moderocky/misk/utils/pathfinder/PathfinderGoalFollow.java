package com.moderocky.misk.utils.pathfinder;

import net.minecraft.server.v1_14_R1.Entity;
import net.minecraft.server.v1_14_R1.EntityCreature;
import net.minecraft.server.v1_14_R1.EntityLiving;
import net.minecraft.server.v1_14_R1.PathfinderGoal;

import java.util.List;

/**
 *
 * Imported from SkStuff.
 * @author TheBukor
 *
 * "It's (an) old code, but it checks out sir!"
 *
 */

public class PathfinderGoalFollow extends PathfinderGoal {
    private EntityCreature follower;
    private EntityLiving followed;
    private Class<?> followedClass;
    private float radius;
    private double speed;
    private boolean isByName;
    private String customName;

    public PathfinderGoalFollow(EntityCreature follower, Class<?> followedClass, float radius, double speed, boolean isByName, String customName) {
        this.follower = follower;
        this.followedClass = followedClass;
        this.radius = radius;
        this.speed = speed;
        this.isByName = isByName;
        this.customName = customName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean a() {
        if (followed == null) {
            List<?> list = follower.world.a((Class<? extends Entity>) followedClass, follower.getBoundingBox().grow(radius, 4.0D, radius));
            if (list.isEmpty()) {
                return false;
            }
            if (isByName) {
                for (Object entity : list) {
                    if (((EntityLiving) entity).getCustomName().equals(customName)) {
                        followed = (EntityLiving) entity;
                        return true;
                    }
                }
            } else {
                followed = (EntityLiving) list.get(0);
                return true;
            }
        }
        return true;
    }
    public boolean shouldExecute() {
        return a();
    }

    @Override
    public boolean b() {
        if (followed.dead) {
            followed = null;
            return false;
        } else if (followed.h(follower) < 9.0D || followed.h(follower) > Math.pow(radius, 2)) {  // h() = distanceSquaredFrom()
            return false;
            /*
                Stop if >3 blocks.

                Todo: Add a teleporting feature for pet usage?
             */
        } else if (isByName) {
            if (!followed.getCustomName().equals(customName)) {
                followed = null;
                return false;
            }
        }
        return follower.getNavigation().n(); // n() means hasNoPath()
    }
    public boolean shouldContinueExecuting() {
        return b();
    }

    @Override
    public void c() {
        follower.getNavigation().a(followed, speed); // a() means moveTo()
    }
    public boolean execute() {
        return b();
    }
}
