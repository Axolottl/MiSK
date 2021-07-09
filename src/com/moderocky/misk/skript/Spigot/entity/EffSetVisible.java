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
 * Make an entity visible.
 *
 * @author Moderocky
 *
 */

@Name("Visible")
@Description("Make any entity visible. Probably only useful if they were already invisible. :)")
@Examples({
        "make all players visible",
        "make target entity visible"
})
@Since("0.2.5")
public class EffSetVisible extends Effect {

    static {
        Skript.registerEffect(EffSetVisible.class, "make %entities% visible");
    }

    @SuppressWarnings("null")
    private Expression<Entity>  entityExpression;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entityExpression = (Expression<Entity>)  exprs[0];
        return true;
    }

    @Override
    protected void execute(Event event) {
        Entity[] entities = entityExpression.getArray(event);
        for (Entity entity : entities) {
            EntityUtils.setInvisible(entity, false);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "make " + entityExpression.toString(e, debug) + " visible";
    }
}