package com.moderocky.misk.utils;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Expression;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

/**
 *
 * Apparently I'm the only one in the world
 * who thinks it might be useful to get
 * the damn ItemStack out of an ItemType!!!
 *
 * And it's beautiful.
 *
 * @author Moderocky
 *
 */

public class SkriptUtils {

    public static ItemStack getItemStack(Expression<ItemType> itemtype, Event event) {
        return itemtype.getSingle(event).getAll().iterator().next();
    }
    public static ItemStack getItemStack(ItemType itemtype) {
        return itemtype.getAll().iterator().next();

    }
}
