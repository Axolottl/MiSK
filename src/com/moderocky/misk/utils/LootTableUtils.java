package com.moderocky.misk.utils;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.MojangsonParser;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.TileEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.v1_14_R1.block.CraftContainer;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootContext.Builder;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;

import java.util.Random;

/**
 * Unfinished.
 *
 * @author Moderocky
 */

public class LootTableUtils {

    /**
        This is mostly Loot Table stuff.
        There are a few other bits and pieces for containers, too!
        See? More bang for your buck.
     */

    public static LootContext newContext(Location location) {
        return new Builder(location).build();
    }
    public static LootContext newContext(Location location, Float luck) {
        return new Builder(location).luck(luck).build();
    }

    public static Random newSeed() {
        return new Random();
    }
    public static Random newSeed(Long seed) {
        return new Random(seed);
    }

    public static void populate(BlockInventoryHolder block) {
        Location    location    = block.getBlock().getLocation();
        LootContext context     = LootTableUtils.newContext(location);
        Random      seed        = LootTableUtils.newSeed();
    }

    public static void populate(Block block) {
        BlockInventoryHolder blockInventoryHolder = (BlockInventoryHolder) block.getState();
        LootTableUtils.populate(blockInventoryHolder);
        blockInventoryHolder.getBlock().getState().update();
    }

    // The Bukkit method is a default. This theoretically replaces it.
    public static Boolean hasLootTable(Lootable lootable) {
        return (lootable.getLootTable() != null);
    }
    private static String safeString(String string) {
        string = string.replace("\"", "");
        string = string.replace("\\", "");
        string = string.replace("{", "");
        string = string.replace("}", "");
        string = string.replace(":", "");
        return string;
    }

    // The Bukkit method is a default. This theoretically replaces it.
    public static void clearLootTable(Lootable lootable) {
        lootable.setLootTable(null);
    }
    public static void setLootTable(Lootable lootable, LootTable loottable) {
        lootable.setLootTable(loottable);
    }
    public static void setLootTable(Container block, LootTable loottable) {
        Lootable lootable = (Lootable) block.getBlock().getState();
        lootable.setLootTable(loottable);
        block.getBlock().getState().update();
    }
    public static void setLootTable(Block block, LootTable loottable) {
        Lootable lootable = (Lootable) block.getState();
        lootable.setLootTable(loottable);
        block.getState().update();
    }
    // The Bukkit method is a default. This theoretically replaces it.
    public static void setLootTable(Lootable lootable, LootTable loottable, Long seed) {
        lootable.setLootTable(loottable);
        lootable.setSeed(seed);
    }

    public static Boolean isLocked(Block block) {
        if (block.getState() instanceof Container) {
            Container lockable = (Container) block.getState();
            return lockable.isLocked();
        }
        // I guess stone is technically not 'locked' but would rather something nicer. :(
        return false;
    }

    /**
     *   1.14 appears to have broken Bukkit's setLock() method.
     *   Sadly, this means we have to improvise.
     *   Below here are like a zillion alternatives.
     *   Some of these don't work.
     *   Others should be avoided.
     *   Attempt at own risk!
     */

    private static void setNMSLock(TileEntity tileentity, String lock) {
        String safeLock = LootTableUtils.safeString(lock);
        // Remove any NBT-breaking chars, to avoid CSE later.
        // Just in case. Should already be checked.
        try {
            NBTTagCompound key = MojangsonParser.parse("{Lock:\"" + safeLock + "\"}");
            tileentity.load(key);
            tileentity.update();
            // Adds the lock via NBT.
        } catch (CommandSyntaxException exception) {
            Bukkit.getLogger().warning(exception.getMessage());
            // Logs an error, if something went wrong.
        }

    }
    private static void attemptLock(Block block, String lock) {
        if (block.getState() instanceof Container) {
            // Anti-doofus detector. :)
            String safeLock = LootTableUtils.safeString(lock);
            // Remove any NBT-breaking chars, to avoid CSE later.
            TileEntity tileentity = ((CraftWorld) block.getWorld()).getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
            if (tileentity != null) {
                LootTableUtils.setNMSLock(tileentity, safeLock);
            } else {
                // Theoretically if it's null we're already in the shit, but just in case.
                Container lockable = (Container) block.getState();
                CraftContainer test = (CraftContainer) lockable;
                CraftBlockEntityState state = (CraftBlockEntityState) block.getState();
                test.setLock(safeLock);
                state.getSnapshotNBT().a(safeLock);
                lockable.setLock(safeLock);
                block.getState().update();
                // This is literally every other possible method.
            }
        }
    }

    public static void lock(Block block, String lock) {
        if (block.getState() instanceof Container) {
            LootTableUtils.attemptLock(block, lock);
        }
    }
    public static void unlock(Block block) {
        if (block.getState() instanceof Container) {
            LootTableUtils.attemptLock(block, "");
        }
    }
}
