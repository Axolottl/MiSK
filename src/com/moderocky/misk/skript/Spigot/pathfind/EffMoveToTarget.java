package com.moderocky.misk.skript.Spigot.pathfind;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.moderocky.misk.utils.EntityUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

/**
 *
 * People keep asking for pathfinders.
 * This isn't perfect, but it's a start!
 *
 * @author Moderocky
 *
 */

@Name("Pathfind to Entity")
@Description({
        "Make an animal walk to an entity."})
@Examples("make {_pig} walk to player at speed 0.6")
@Since("0.2.0")
public class EffMoveToTarget extends Effect {
    private final static Double DEFAULT_SPEED = 1.;
    // Too fast they overshoot. 1 is fine.

    private Expression<LivingEntity> entityExpression;
    private Expression<Entity> entityExpression2;
    @Nullable
    private Expression<Number> numberExpression;

    static {
        Skript.registerEffect(EffMoveToTarget.class, "make %livingentities% (pathfind|walk) to %entity% [at speed %-number%]");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
        entityExpression = (Expression<LivingEntity>) expr[0];
        entityExpression2 = (Expression<Entity>) expr[1];
        numberExpression = (Expression<Number>) expr[2];
        return true;
    }

    @SuppressWarnings("null")
    @Override
    protected void execute(Event event) {
        Number v = numberExpression != null ? numberExpression.getSingle(event) : DEFAULT_SPEED;
        Double speed = (Double) v;
        if (speed == null)
            return;
        // "How could I be in space, Ted?"
        Entity target = entityExpression2.getSingle(event);
        LivingEntity[] entities = entityExpression.getAll(event);
        for (LivingEntity entity : entities) {
            EntityUtils.moveToPosition(entity, target, speed);
            // Should really implement a distance check, but it's so arbitrary I can't.
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (numberExpression.getSingle(event) != null) {
            return "make " + entityExpression.toString(event, debug) + " walk to " + entityExpression2.toString(event, debug) + " at speed " + numberExpression.toString(event, debug);
        } else {
            return "make " + entityExpression.toString(event, debug) + " walk to " + entityExpression2.toString(event, debug);
        }
    }
}
