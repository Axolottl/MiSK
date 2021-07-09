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

@Name("Stop Pathfinding")
@Description({
        "Make an animal stop pathfinding. Only cancels a navigational goal."})
@Examples("make {_pig} stop pathfinding")
@Since("0.2.0")
public class EffStopPath extends Effect {

    private Expression<LivingEntity> entityExpression;

    static {
        Skript.registerEffect(EffStopPath.class, "make %livingentities% stop (pathfind|walk)ing");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
        entityExpression = (Expression<LivingEntity>) expr[0];
        return true;
    }

    @SuppressWarnings("null")
    @Override
    protected void execute(Event event) {
        LivingEntity[] entities = entityExpression.getAll(event);
        for (LivingEntity entity : entities) {
            EntityUtils.stopPathfinding(entity);
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "make " + entityExpression.toString(event, debug) + " stop pathfinding";
    }
}
