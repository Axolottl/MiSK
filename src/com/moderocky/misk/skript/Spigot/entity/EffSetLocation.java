package com.moderocky.misk.skript.Spigot.entity;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.moderocky.misk.utils.EntityUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

/**
 *
 * Move an entity.
 *
 * @author Moderocky
 *
 */

@Name("Set Location / Move Entity")
@Description("Move an entity. This will take all passengers/hangers-on with the entity. " +
        "MUST be in the same world.")
@Examples({
        "move player's vehicle to {spawn-point}"
})
@Since("0.2.5")
public class EffSetLocation extends Effect {

    static {
        Skript.registerEffect(EffSetLocation.class, "move %entities% to %location%");
    }

    @SuppressWarnings("null")
    private Expression<Entity>   entityExpression;
    private Expression<Location> locationExpression;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entityExpression   = (Expression<Entity>)   exprs[0];
        locationExpression = (Expression<Location>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event event) {
        Entity[] entities = entityExpression.getArray(event);
        Location location = locationExpression.getSingle(event);
        for (Entity entity : entities) {
            if (entity.getWorld() == location.getWorld()) {
                EntityUtils.setLocation(entity, location);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "move " + entityExpression.toString(e, debug) + " to " + locationExpression.toString(e, debug);
    }
}