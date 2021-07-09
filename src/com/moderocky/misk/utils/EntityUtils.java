package com.moderocky.misk.utils;

import com.moderocky.misk.utils.pathfinder.Navigation;
import net.minecraft.server.v1_14_R1.AxisAlignedBB;
import net.minecraft.server.v1_14_R1.EntityAnimal;
import net.minecraft.server.v1_14_R1.EntityCreature;
import net.minecraft.server.v1_14_R1.EntityInsentient;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EntityUtils {

    public static void leash(Entity entity, Player player) {
        EntityInsentient insentient = (EntityInsentient) ((CraftEntity) entity).getHandle();
        insentient.setLeashHolder(((CraftEntity) player).getHandle(), true);
    }
    public static void leash(Entity entity, Entity holder) {
        if (holder instanceof Player) {
            leash(entity, (Player) holder);
        } else {
            EntityInsentient insentient = (EntityInsentient) ((CraftEntity) entity).getHandle();
            insentient.setLeashHolder(((CraftEntity) holder).getHandle(), false);
        }
    }

    public static void moveToPosition(Entity entity, Location location, Double speed) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        if ((EntityInsentient) nmsEntity != null) {
            EntityInsentient entityInsentient = (EntityInsentient) nmsEntity;
            Navigation.moveToPosition(entityInsentient, location, speed);
        } else {
            Navigation.moveToPosition(nmsEntity, location, speed);
        }
    }
    public static void moveToPosition(Entity entity, Entity target, Double speed) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        net.minecraft.server.v1_14_R1.Entity nmsTarget = ((CraftEntity) target).getHandle();
        if ((EntityInsentient) nmsEntity != null) {
            EntityInsentient entityInsentient = (EntityInsentient) nmsEntity;
            Navigation.moveToPosition(entityInsentient, nmsTarget, speed);
        } else {
            Navigation.moveToPosition(nmsEntity, nmsTarget, speed);
        }
    }

    public static void stopPathfinding(Entity entity) {
        Navigation.stopPathfinding(((CraftEntity) entity).getHandle());
    }

    /**
     * This was a pain in the ass.
     * So they removed setSize() in 1.14
     * Instead, I now have to guess the bounding box usage.
     *
     * @param entity A Bukkit entity
     * @param width  The hitbox width
     * @param height The hitbox height
     *
     * Now we guess it by vectors. :)
     *
     */
    public static void setHitBox(Entity entity, Number width, Number height) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        Double d0 = (Double) width/2*-1;
        Double d1 = (Double) 0.0;
        Double d2 = (Double) width/2*-1;
        Double d3 = (Double) width/2;
        Double d4 = (Double) height;
        Double d5 = (Double) width/2;
        AxisAlignedBB axisAlignedBB = nmsEntity.getBoundingBox();
        axisAlignedBB.a(d0, d1, d2, d3, d4, d5);
        nmsEntity.a(axisAlignedBB);
    }
    public static void growHitBox(Entity entity, Number number) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        Double d0 = (Double) number;
        AxisAlignedBB axisAlignedBB = nmsEntity.getBoundingBox();
        axisAlignedBB.grow(d0);
        nmsEntity.a(axisAlignedBB);
    }
    public static void shrinkHitBox(Entity entity, Number number) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        Double d0 = (Double) number;
        AxisAlignedBB axisAlignedBB = nmsEntity.getBoundingBox();
        axisAlignedBB.shrink(d0);
        nmsEntity.a(axisAlignedBB);
    }

    /**
     * Illegal mounting of entities.
     *
     * "Don't lecture me, Obi-wan, I see through the lies of the Jedi!"
     *
     * Okay, but be super careful with this!!!
     *
     * @param entity  The passenger
     * @param vehicle The vehicle
     */
    public static void mount(Entity entity, Entity vehicle) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity  = ((CraftEntity) entity).getHandle();
        net.minecraft.server.v1_14_R1.Entity nmsVehicle = ((CraftEntity) vehicle).getHandle();
        nmsEntity.a(nmsVehicle, true);
    }

    /**
     * This gets the actual driver, when there are multiple passengers.
     * @param entity The vehicle.
     * @return The driver.
     */
    public static Entity getRider(Entity entity) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        net.minecraft.server.v1_14_R1.Entity nmsRider  = nmsEntity.getRidingPassenger();
        CraftServer craftServer = ((CraftServer) Bukkit.getServer());
        Entity rider            = (Entity) CraftEntity.getEntity(craftServer, nmsRider);
        return rider;
    }

    /**
     * This gets the actual lowest vehicle, when there are stacked vehicles.
     * @param entity The entity.
     * @return The vehicle.
     */
    public static Entity getRootVehicle(Entity entity) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        net.minecraft.server.v1_14_R1.Entity nmsRider  = nmsEntity.getRootVehicle();
        CraftServer craftServer = ((CraftServer) Bukkit.getServer());
        Entity vehicle          = (Entity) CraftEntity.getEntity(craftServer, nmsRider);
        return vehicle;
    }

    /**
     * If this thing is a vehicle.
     * @param entity The tested entity
     * @return True/False
     */
    public static Boolean isVehicle(Entity entity) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        return nmsEntity.isVehicle();
    }

    /**
     * If this entity is a passenger.
     * @param entity The tested entity
     * @return True/False
     */
    public static Boolean isPassenger(Entity entity) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        return nmsEntity.isPassenger();
    }

    /**
     * If this entity can be interacted with.
     * @param entity The tested entity
     * @return True/False
     */
    public static Boolean isInteractable(Entity entity) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        return nmsEntity.isInteractable();
    }

    /**
     * If this entity is on fire.
     * @param entity The tested entity
     * @return True/False
     */
    public static Boolean isBurning(Entity entity) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        return nmsEntity.isBurning();
    }

    /**
     * If this entity is fire proof.
     * @param entity The tested entity
     * @return True/False
     */
    public static Boolean isFireProof(Entity entity) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        return nmsEntity.isFireProof();
    }

    /**
     * If this entity is in water or rain.
     * @param entity The tested entity
     * @return True/False
     */
    public static Boolean isWet(Entity entity) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        return nmsEntity.isInWaterOrRain();
    }

    /**
     * If this player can use channeling/riptide.
     * Technically the same as isWet(), for now.
     * @param player The tested entity
     * @return True/False
     */
    public static Boolean canUseTrident(Player player) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) player).getHandle();
        return nmsEntity.isInWaterOrRain();
    }

    /**
     * If this entity is in water.
     * @param entity The tested entity
     * @return True/False
     */
    public static Boolean isInWater(Entity entity) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        return nmsEntity.isInWater();
    }

    /**
     * Make any entity invisible.
     * @param entity The target
     * @param bool   True or false
     */
    public static void setInvisible(Entity entity, Boolean bool) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        nmsEntity.setInvisible(bool);
    }

    /**
     * Make any entity swim.
     * @param entity The target
     * @param bool   True or false
     */
    public static void setSwimming(Entity entity, Boolean bool) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        nmsEntity.setSwimming(bool);
    }
    public static Boolean isSwimming(Entity entity) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        return nmsEntity.isSwimming();
    }

    /**
     * These two are actually super useful!
     * Bukkit teleporting causes visual issues when done repeatedly to players.
     * It also upsets pathfinding, vehicles, etc.
     *
     * These two shift the exact entity to an in-world location.
     * No mess, no fuss, no trouble.
     *
     * @param entity   The poor sod you're gonna be teleporting
     * @param location The place you want it (IN-WORLD!)
     */
    public static void setLocation(Entity entity, Location location) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        Double x = location.getX();
        Double y = location.getY();
        Double z = location.getZ();
        Float a0 = location.getYaw();
        Float a1 = location.getPitch();
        nmsEntity.setLocation(x, y, z, a0, a1);
    }
    public static void setPosition(Entity entity, Location location) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        Double x = location.getX();
        Double y = location.getY();
        Double z = location.getZ();
        nmsEntity.setPosition(x, y, z);
    }

    /**
     * Make any entity silent.
     * @param entity The target
     * @param bool   True or false
     */
    public static void setSilent(Entity entity, Boolean bool) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        nmsEntity.setSilent(bool);
    }

    /**
     * Did you know it's veeeery very annoying to have to cast every time?
     *
     * @param entity The entity. Either a Bukkit entity or an NMS Entity.
     * @return Reality can be whatever I want.
     */
    public static CraftEntity craftEntity(Entity entity) {
        return (CraftEntity) entity;
    }

    public static net.minecraft.server.v1_14_R1.Entity nmsEntity(Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }

    public static EntityInsentient entityInsentient(Entity entity) {
        return (EntityInsentient) ((CraftEntity) entity).getHandle();
    }

    public static EntityInsentient entityInsentient(net.minecraft.server.v1_14_R1.Entity entity) {
        return (EntityInsentient) entity;
    }

    public static EntityAnimal entityAnimal(Entity entity) {
        return (EntityAnimal) ((CraftEntity) entity).getHandle();
    }

    public static EntityAnimal entityAnimal(net.minecraft.server.v1_14_R1.Entity entity) {
        return (EntityAnimal) entity;
    }

    public static EntityCreature entityCreature(Entity entity) {
        return (EntityCreature) ((CraftEntity) entity).getHandle();
    }

    public static EntityCreature entityCreature(net.minecraft.server.v1_14_R1.Entity entity) {
        return (EntityCreature) entity;
    }
}
