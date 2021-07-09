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

@Name("Set Collision Box Size")
@Description("Set the entity's server-side collision box. This affects mobility only.")
@Examples({
        "set collision box of {_stand} to 3 wide and 2 tall"
})
@Since("0.2.5")
public class EffSetCollisionBox extends Effect {

    /*
    static {
        Skript.registerEffect(EffSetCollisionBox.class, "set (collision |hit[ ])box of %entities% to %number% [wide] [(and|by) %-number% [tall]]");
    }

     */

    @SuppressWarnings("null")
    private Expression<Entity> entityExpression;
    private Expression<Number> numberExpression;
    @Nullable
    private Expression<Number> numberExpression2;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entityExpression   = (Expression<Entity>) exprs[0];
        numberExpression   = (Expression<Number>) exprs[1];
        numberExpression2  = (Expression<Number>) exprs[2];
        return true;
    }

    @Override
    protected void execute(Event event) {
        Entity[] entities = entityExpression.getArray(event);
        Number   width    = numberExpression.getSingle(event);
        Number height = numberExpression.getSingle(event);
        if (numberExpression2.getSingle(event) != null) {
            height = numberExpression2.getSingle(event);
        }
        for (Entity entity : entities) {
            EntityUtils.setHitBox(entity, width, height);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        if (numberExpression2.toString(e, debug) != null) {
            return "set collision box of " + entityExpression.toString(e, debug) + " to " + numberExpression.toString(e, debug) + " wide and " + numberExpression2.toString(e, debug) + " tall";
        } else {
            return "set collision box of " + entityExpression.toString(e, debug) + " to " + numberExpression.toString(e, debug) + " wide and " + numberExpression.toString(e, debug) + " tall";
        }
    }
}