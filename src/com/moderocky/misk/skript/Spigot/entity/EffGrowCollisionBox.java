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
 * NOT IMPLEMENTED
 *
 * Change an entity's collision box size.
 *
 * @author Moderocky
 *
 */

@Name("Grow Collision Box")
@Description("Grows the entity's server-side collision box. This affects mobility only.")
@Examples({
        "grow collision box of {_stand} by 1"
})
@Since("0.2.5")
public class EffGrowCollisionBox extends Effect {

    /*
    static {
        Skript.registerEffect(EffGrowCollisionBox.class, "grow (collision |hit[ ])box of %entities% by %number%");
    }

     */

    @SuppressWarnings("null")
    private Expression<Entity> entityExpression;
    private Expression<Number> numberExpression;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entityExpression   = (Expression<Entity>) exprs[0];
        numberExpression   = (Expression<Number>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event event) {
        Entity[] entities = entityExpression.getArray(event);
        Number   size     = numberExpression.getSingle(event);
        for (Entity entity : entities) {
            EntityUtils.growHitBox(entity, size);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "grow collision box of " + entityExpression.toString(e, debug) + " by " + numberExpression.toString(e, debug);
    }
}