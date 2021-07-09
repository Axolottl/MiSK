package com.moderocky.misk.skript.Spigot.misc;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.moderocky.misk.utils.ItemStackUtils;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.v1_14_R1.ItemStack;
import net.minecraft.server.v1_14_R1.MojangsonParser;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * So I already had a class to access the constructor for this.
 * Technically, it's NBT-ish stuff, but I was going to make an item "serialiser" function.
 *
 * @author Moderocky
 *
 */

@Name("Item From String")
@Description({
        "Get the item from an NBT string, such as returned from Minecraft's /data command"})
@Examples("set {_i} to item from nbt string {_string}")
@Since("0.2.0")
public class ExprItemFromNBT extends SimpleExpression<ItemType> {
    private Expression<String> string;
    private ItemStack itemStack;
    private NBTTagCompound nbtTagCompound;

    static {
        Skript.registerExpression(ExprProjectileItem.class, ItemType.class, ExpressionType.SIMPLE,
                "[the] item (from|of) nbt [string] %string%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.string = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected ItemType[] get(Event event) {
        List<ItemType> itemTypes = new ArrayList<>();
        try {
            nbtTagCompound = MojangsonParser.parse(string.getSingle(event));
            if (nbtTagCompound != null)
                itemStack = ItemStackUtils.createItemStack(nbtTagCompound);
        } catch(IllegalAccessException | InstantiationException | InvocationTargetException | CommandSyntaxException exception){
            // And ignore it :)
        }
        if (itemStack != null) {
                    /*
                        So, I could do itemStack.asBukkitCopy(), but I know this method works.
                     */
            itemTypes.add(new ItemType(CraftItemStack.asBukkitCopy(itemStack)));
            return itemTypes.toArray(new ItemType[0]);
        }
        // If something goes wrong, we return some air!
        itemTypes.add(new ItemType(new org.bukkit.inventory.ItemStack(Material.AIR)));
        return itemTypes.toArray(new ItemType[0]);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ItemType> getReturnType() {
        return ItemType.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "item from nbt " + string.toString(event, debug);
    }

}
