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
import org.bukkit.Location;
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

@Name("Pathfind to Location")
@Description({
        "Make an animal walk to a location."})
@Examples("make {_pig} walk to {_location} at speed 0.6")
@Since("0.2.0")
public class EffPathfind extends Effect {
    private final static Double DEFAULT_SPEED = 1.;
    // Too fast they overshoot. 1 is fine.

    private Expression<LivingEntity> entityExpression;
    @Nullable
    private Expression<Number> numberExpression;
    private Expression<Location> locationExpression;

    static {
        Skript.registerEffect(EffPathfind.class, "make %livingentities% (pathfind|walk) to %location% [at speed %-number%]");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
        entityExpression = (Expression<LivingEntity>) expr[0];
        locationExpression = (Expression<Location>) expr[1];
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
        Location location = locationExpression.getSingle(event);
        LivingEntity[] entities = entityExpression.getAll(event);
        for (LivingEntity entity : entities) {
            EntityUtils.moveToPosition(entity, location, speed);
            // Should really implement a distance check, but it's so arbitrary I can't.
            /*
                "But hey!" I hear you say.
                "What about Armour Stands and Players? Aren't they LivingEntities?"

                Yes, indeed.
                And technically there's a reflective method for non-EntityInsentients.

                "Does it work?"
                Nope. But we can pretend. :)
             */
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (numberExpression.getSingle(event) != null) {
            return "make " + entityExpression.toString(event, debug) + " walk to " + locationExpression.toString(event, debug) + " at speed " + numberExpression.toString(event, debug);
        } else {
            return "make " + entityExpression.toString(event, debug) + " walk to " + locationExpression.toString(event, debug);
        }
    }
}
