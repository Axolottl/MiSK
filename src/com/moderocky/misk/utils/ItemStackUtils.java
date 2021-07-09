package com.moderocky.misk.utils;

import ch.njol.skript.aliases.ItemType;
import com.moderocky.misk.MiSK;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.TreeMap;

/**
 * This is mostly weird, strange, NBT stuff.
 * I'm also going to work on some 1.14 item-ish things here as well.
 * PersistentDataContainers and CustomModelData here I come!
 */
public class ItemStackUtils {
    static TreeMap<String, ItemStack> customList;
    public static net.minecraft.server.v1_14_R1.ItemStack itemStack;

    /*
        1.14 stuff.
     */

    /**
     * Aha! Here we find the magic method. Making an ItemStack from its NBT blob.
     * Some Moof Milker at Mojang decided to make the constructor private.
     *
     * As Obi-Wan would say... "Oh, I don't think so!"
     *
     * @param nbtTagCompound The NBT compound representing the item.
     * @return An NMSItemStack.
     * @throws InstantiationException    This means things went wrong.
     * @throws IllegalAccessException    This means reflection broke down.
     * @throws IllegalArgumentException  This means some doofus used the wrong thing.
     * @throws InvocationTargetException I have literally no idea what this means.
     */
    public static net.minecraft.server.v1_14_R1.ItemStack createItemStack(NBTTagCompound nbtTagCompound) throws InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Constructor<?>[] constructors = net.minecraft.server.v1_14_R1.ItemStack.class.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (Modifier.isPrivate(constructor.getModifiers())) {
                constructor.setAccessible(true);
                Class<?>[] classes = constructor.getParameterTypes();
                if (constructor.getParameterCount() == 1 && classes[0] == NBTTagCompound.class) {
                    Object object = constructor.newInstance(nbtTagCompound);
                    if (object instanceof net.minecraft.server.v1_14_R1.ItemStack) {
                        itemStack = (net.minecraft.server.v1_14_R1.ItemStack) object;
                        return itemStack;
                    }
                }
            }
        }
        if (itemStack != null) {
            return itemStack;
        } else {
            return null;
        }
    }

    public static ItemStack createBukkitItem(NBTTagCompound nbtTagCompound) {
        try {
            net.minecraft.server.v1_14_R1.ItemStack itemStack = createItemStack(nbtTagCompound);
            if (itemStack != null) {
                return itemStack.asBukkitCopy();
            }
        } catch (Exception e) {
            //Foof!
        }
        return new ItemStack(Material.AIR);
        // You done messed up, Mahone!
    }

    public static void setModelData(ItemStack itemStack, Integer integer) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(integer);
        itemStack.setItemMeta(itemMeta);
    }
    public static void setModelData(ItemType itemType, Integer integer) {
        setModelData(SkriptUtils.getItemStack(itemType), integer);
    }

    public static Boolean hasModelData(ItemStack itemStack) {
        return itemStack.getItemMeta().hasCustomModelData();
    }
    public static Boolean hasModelData(ItemType itemType) {
        return SkriptUtils.getItemStack(itemType).getItemMeta().hasCustomModelData();
    }

    public static Integer getModelData(ItemStack itemStack) {
        return itemStack.getItemMeta().getCustomModelData();
    }
    public static Integer getModelData(ItemType itemType) {
        return SkriptUtils.getItemStack(itemType).getItemMeta().getCustomModelData();
    }

    public static void setUnbreakable(ItemStack itemStack, Boolean b) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(b);
        itemStack.setItemMeta(itemMeta);
    }
    public static void setUnbreakable(ItemType itemType, Boolean b) {
        SkriptUtils.getItemStack(itemType).getItemMeta().setUnbreakable(b);
    }

    public static void setLocalisedName(ItemStack itemStack, String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLocalizedName(name);
        itemStack.setItemMeta(itemMeta);
    }
    public static void setLocalisedName(ItemType itemType, String name) {
        SkriptUtils.getItemStack(itemType).getItemMeta().setLocalizedName(name);
    }

    public static String getLocalisedName(ItemStack itemStack) {
        return itemStack.getItemMeta().getLocalizedName();
    }
    public static String getLocalisedName(ItemType itemType) {
        return SkriptUtils.getItemStack(itemType).getItemMeta().getLocalizedName();
    }

    public static PersistentDataContainer getDataContainer(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer();
    }
    public static PersistentDataContainer getDataContainer(ItemType itemType) {
        return SkriptUtils.getItemStack(itemType).getItemMeta().getPersistentDataContainer();
    }

    public static void setValue(PersistentDataContainer container, NamespacedKey key, PersistentDataType type, Object value) {
        Class<?> clazz = type.getClass();
        Class<?> trueValue = (Class<?>) clazz.cast(value);
        container.set(key, type, trueValue);
        /*
            Literally no clue if this will work.
            As an alternative, we have eight lovely methods below for individuals. :D
            I can't *wait* for all those if statements, can you?
         */
    }
    public static void setByteValue(PersistentDataContainer container, NamespacedKey key, Byte value) {
        container.set(key, PersistentDataType.BYTE, value);
    }
    public static void setFloatValue(PersistentDataContainer container, NamespacedKey key, Float value) {
        container.set(key, PersistentDataType.FLOAT, value);
    }
    public static void setStringValue(PersistentDataContainer container, NamespacedKey key, String value) {
        container.set(key, PersistentDataType.STRING, value);
    }
    public static void setShortValue(PersistentDataContainer container, NamespacedKey key, Short value) {
        container.set(key, PersistentDataType.SHORT, value);
    }
    public static void setDoubleValue(PersistentDataContainer container, NamespacedKey key, Double value) {
        container.set(key, PersistentDataType.DOUBLE, value);
    }
    public static void setIntegerValue(PersistentDataContainer container, NamespacedKey key, Integer value) {
        container.set(key, PersistentDataType.INTEGER, value);
    }
    public static void setLongValue(PersistentDataContainer container, NamespacedKey key, Long value) {
        container.set(key, PersistentDataType.LONG, value);
    }
    public static void setContainerValue(PersistentDataContainer container, NamespacedKey key, PersistentDataContainer value) {
        container.set(key, PersistentDataType.TAG_CONTAINER, value);
    }

    /*
        Semi-legal methods for registering keys quickly and simply.
        Don't try this at home.

        Public since you can literally do it in a single line anyway.
     */

    public static NamespacedKey newKey(Plugin plugin, String key) {
        return new NamespacedKey(plugin, key);
        // Key from plugin.
    }
    public static NamespacedKey newKey(String plugin, String key) {
        Plugin truePlugin = Bukkit.getPluginManager().getPlugin(plugin);
        if (truePlugin != null) {
            return new NamespacedKey(truePlugin, key);
        } else {
            truePlugin = Bukkit.getPluginManager().getPlugin(MiSK.getPluginName());
            return new NamespacedKey(truePlugin, key);
            // Well I'd better damn well hope it isn't null!
        }
        // Key from string.
    }
    public static NamespacedKey newMiskKey(String key) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(MiSK.getPluginName());
        return new NamespacedKey(plugin, key);
        // Registering items, etc.
    }
    public static NamespacedKey newSkriptKey(String key) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Skript");
        return new NamespacedKey(plugin, key);
        // Internal use only.
        // Since anybody could do this, no point making it private.
    }
    /*
        Damn Bukkit noticed this and told me off for it :(

    @SuppressWarnings("deprecated")
    public static NamespacedKey newIllegalKey(String namespace, String key) {
        return new NamespacedKey(namespace, key);
        // No, Anakin, no!
        // Don't lecture me, Obi-wan! I do not fear the dark side as you do.
    }

     */

    public static void setup() {
        customList = new TreeMap<>();
    }
    public static void deleteEntry(String id) {
        customList.remove(id);
    }
    public static void saveItemStack(String id, ItemStack itemStack) {
        customList.put(id, itemStack);
    }
    public static void saveItemStack(String id, ItemType itemType) {
        ItemStack itemStack = SkriptUtils.getItemStack(itemType);
        customList.put(id, itemStack);
    }

    public static TreeMap getItemList() {
        return customList;
    }

    public static ItemStack getItemStack(String id) {
        if (getItemList().containsKey(id)) {
            if ((ItemStack) getItemList().get(id) != null) {
                return (ItemStack) getItemList().get(id);
            }
        }
        return new ItemStack(Material.AIR);
    }
    public static ItemType getItemType(String id) {
        if (getItemList().containsKey(id)) {
            if ((ItemStack) getItemList().get(id) != null) {
                return new ItemType((ItemStack) getItemList().get(id));
            }
        }
        return new ItemType(new ItemStack(Material.AIR));
    }
}
