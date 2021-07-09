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
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

/**
 * Making use of 1.14's snowball textures.
 * I had to branch out into some NBT-esque stuff for this, but I didn't go too far down the rabbit hole.
 * My addon is designed for 1.14 things, it'd be a shame to miss this out.
 *
 * @author Moderocky
 */

@Name("Shoot Item")
@Description("This shoots a snowball with a custom item model on it, of the item used in the syntax.")
@Examples({
        "make player shoot stick at speed 2"
})
@Since("0.2.0")
public class EffShootItem extends Effect {
    private final static Double DEFAULT_SPEED = 1.;
    // Skript got this bit wrong.
    // Don't shoot a snowball at fifty fucking blocks per second...
    private Expression<ItemType> itemTypeExpression;
    private Expression<LivingEntity> entityExpression;
    @Nullable
    private Expression<Number> numberExpression;
    @Nullable
    private Expression<Direction> directionExpression;
    private String itemString;

    @Nullable
    public static Entity lastSpawned = null;

    static {
        Skript.registerEffect(EffShootItem.class, "make %entities% shoot %itemtype% [at speed %-number%] [%-direction%]");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
        entityExpression = (Expression<LivingEntity>) expr[0];
        itemTypeExpression = (Expression<ItemType>) expr[1];
        numberExpression = (Expression<Number>) expr[2];
        directionExpression = (Expression<Direction>) expr[3];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (numberExpression.getSingle(event) != null) {
            return "make " + entityExpression.toString(event, debug) + " shoot " + itemTypeExpression.toString(event, debug) + " at speed " + numberExpression.toString(event, debug) + (directionExpression != null ? " " + directionExpression.toString(event, debug) : "");
        } else {
            return "make " + entityExpression.toString(event, debug) + " shoot " + itemTypeExpression.toString(event, debug) + (directionExpression != null ? " " + directionExpression.toString(event, debug) : "");
        }
    }

    @SuppressWarnings("null")
    @Override
    protected void execute(Event event) {
        lastSpawned = null;
        // Copied this from Skript's default. If it ain't broke, don't fix!
        final Number v = numberExpression != null ? numberExpression.getSingle(event) : DEFAULT_SPEED;
        if (v == null)
            return;
        itemString = CraftItemStack.asNMSCopy(SkriptUtils.getItemStack(itemTypeExpression.getSingle(event))).save(new NBTTagCompound()).toString();
        final Direction dir = directionExpression != null ? directionExpression.getSingle(event) : Direction.IDENTITY;
        LivingEntity[] entities = entityExpression.getAll(event);
        for (LivingEntity entity : entities) {
            final Vector vel = dir.getDirection(entity.getLocation()).multiply(v.doubleValue());
            @SuppressWarnings("unchecked")
            final Projectile projectile = entity.launchProjectile(Snowball.class, vel);
            //projectile.setVelocity(vel);
            try {
                net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity) projectile).getHandle();
                NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nmsEntity.c(nbtTagCompound);
                NBTTagCompound nbtTagCompound1 = MojangsonParser.parse("{Item:" + itemString + "}");
                nbtTagCompound.a(nbtTagCompound1);
                nmsEntity.f(nbtTagCompound);
            } catch (CommandSyntaxException exception) {
                // Ignore.
            }
            lastSpawned = projectile;
        }
    }
}
