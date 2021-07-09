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
 * Alter an entity's swimming state.
 *
 * @author Moderocky
 *
 */

@Name("Invisible")
@Description("Alter an entity's swimming state.")
@Examples({
        "set swimming state of all players to true",
        "set the swimming state of target entity to false"
})
@Since("0.2.5")
public class EffSwimmingState extends Effect {

    static {
        Skript.registerEffect(EffSwimmingState.class, "set [the] swimming state of %entities% to %boolean%");
    }

    @SuppressWarnings("null")
    private Expression<Entity>  entityExpression;
    private Expression<Boolean> booleanExpression;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entityExpression   = (Expression<Entity>)  exprs[0];
        booleanExpression  = (Expression<Boolean>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event event) {
        Entity[] entities = entityExpression.getArray(event);
        Boolean  aBoolean = booleanExpression.getSingle(event);
        for (Entity entity : entities) {
            EntityUtils.setInvisible(entity, aBoolean);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "set the swimming state of " + entityExpression.toString(e, debug) + " to " + booleanExpression.toString(e, debug);
    }
}