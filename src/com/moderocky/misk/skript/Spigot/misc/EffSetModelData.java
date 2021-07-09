package com.moderocky.misk.skript.Spigot.misc;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import com.moderocky.misk.utils.ItemStackUtils;
import com.moderocky.misk.utils.SkriptUtils;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.v1_14_R1.MojangsonParser;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

/**
 * Making use of 1.14's snowball textures.
 * I had to branch out into some NBT-esque stuff for this, but I didn't go too far down the rabbit hole.
 * My addon is designed for 1.14 things, it'd be a shame to miss this out.
 *
 * @author Moderocky
 */

@Name("Custom Model Data")
@Description("Set the custom model data of an itemstack.")
@Examples({
        "set model data of player's tool to 6"
})
@Since("1.0.5")

public class EffSetModelData extends Effect {
    private Expression<ItemType> itemTypeExpression;
    @Nullable
    private Expression<Number>   numberExpression;
    private Boolean              reset;

    static {
        Skript.registerEffect(EffSetModelData.class,
                "set [the] [custom] model data of %itemtypes% to %number%",
                "set %itemtypes%'[s] [custom] model data to %number%",
                "reset [the] [custom] model data of %itemtypes%",
                "reset %itemtypes%'[s] [custom] model data"
        );
    }

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
        reset = (matchedPattern == 3 || matchedPattern == 4);
        itemTypeExpression = (Expression<ItemType>) expr[0];
        if (!reset) {
            numberExpression = (Expression<Number>) expr[1];
        }
        return true;
    }

    @SuppressWarnings({"null"})
    @Override
    protected void execute(Event event) {
        ItemType[] itemTypes = itemTypeExpression.getArray(event);
        if (reset) {
            for (ItemType itemType : itemTypes) {
                ItemStackUtils.setModelData(itemType, 0);
            }
        } else if (numberExpression != null) {
            for (ItemType itemType : itemTypes) {
                ItemStackUtils.setModelData(itemType, numberExpression.getSingle(event).intValue());
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (reset) {
            return "reset model data of " + itemTypeExpression.toString(event, debug);
        } else if (numberExpression != null) {
            return "set model data of " + itemTypeExpression.toString(event, debug) + " to " + numberExpression.toString(event, debug);
        }
        return "Error";
    }
}
