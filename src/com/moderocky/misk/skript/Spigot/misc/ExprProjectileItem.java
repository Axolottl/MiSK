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
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Technically, this is NBT-ish stuff, but it would be stupid not to include it, given the "Shoot Item" effect.
 * Also, I already had the constructor method for my serialiser project.
 *
 * @author Moderocky
 *
 */

@Name("Displayed Item on Projectile")
@Description({
        "Get itemstack displayed on a snowball/etc."})
@Examples("set {_i} to item of projectile {_e}")
@Since("0.2.0")
public class ExprProjectileItem extends SimpleExpression<ItemType> {
    private Expression<Entity> entity;
    @Nullable
    private ItemStack itemStack;
    @Nullable
    private NBTTagCompound nbtTagCompound;

    static {
        Skript.registerExpression(ExprProjectileItem.class, ItemType.class, ExpressionType.SIMPLE,
                "[the] item (of|displayed on) [projectile] %entity%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.entity = (Expression<Entity>) exprs[0];
        return true;
    }

    /*
        I am not 100% sure which projectiles accept a displayed item.
        I have a feeling it's just item-style ones, maybe XP bottles, or potions. But I'm not sure.
        I've included all the item-ish ones, for good measure! :)
        They'll probably return null anyway.
     */

    @Override
    protected ItemType[] get(Event event) {
        List<ItemType> itemTypes = new ArrayList<>();
        if (entity.getSingle(event) instanceof Snowball || entity.getSingle(event) instanceof EnderPearl || entity.getSingle(event) instanceof Egg || entity.getSingle(event) instanceof ThrownExpBottle || entity.getSingle(event) instanceof ThrownPotion) {
            net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
            NBTTagCompound nbt = new NBTTagCompound();
            nmsEntity.c(nbt);
            if (nbt.get("Item") != null) {
                String itemString = nbt.get("Item").toString();
                try {
                    nbtTagCompound = MojangsonParser.parse(itemString);
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
            }
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
        return "item of projectile " + entity.toString(event, debug);
    }

}
