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
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

/**
 *
 * Illegally mount an entity on another entity, irrespective of any limits.
 *
 * @author Moderocky
 *
 */

@Name("Force Mount")
@Description("Forces an entity to mount another. Be careful! All of these entities will send vehicle steer packets to the server. Use it wisely.")
@Examples({
        "mount {_pigs::*} on {_stand}",
        "mount all players on target entity"
})
@Since("0.2.5")
public class EffMount extends Effect {

    static {
        Skript.registerEffect(EffMount.class, "mount %entities% on %entity%");
    }

    @SuppressWarnings("null")
    private Expression<Entity> entityExpression;
    private Expression<Entity> entityExpression1;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entityExpression   = (Expression<Entity>) exprs[0];
        entityExpression1  = (Expression<Entity>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event event) {
        Entity[] entities = entityExpression.getArray(event);
        Entity   target   = entityExpression1.getSingle(event);
        for (Entity entity : entities) {
            EntityUtils.mount(entity, target);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "mount " + entityExpression.toString(e, debug) + " on " + entityExpression1.toString(e, debug);
    }
}