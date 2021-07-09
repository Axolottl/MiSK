package com.moderocky.misk.skript.Spigot.merchant;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.moderocky.misk.utils.MerchantUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.Event;

import javax.annotation.Nullable;


@Name("Set Villager Level")
@Description("Sets the level of a villager.")
@Examples({
        "set level of {_v} to 5",
        "set level of {_v} to 1"
})
@Since("1.0.0")
public class EffSetVillagerLevel extends Effect {

    static {
        Skript.registerEffect(EffSetVillagerLevel.class,
                "set [the] level of [villager] %entities% to %number%"
        );
    }

    @SuppressWarnings("null")
    private Expression<Entity>          entityExpression;
    private Expression<Number>          numberExpression;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entityExpression = (Expression<Entity>) exprs[0];
        numberExpression = (Expression<Number>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        for (Entity entity : entityExpression.getArray(e)) {
            if (entity instanceof Villager) {
                Villager trader = (Villager) entity;
                MerchantUtils.setLevel(trader, numberExpression.getSingle(e).intValue());
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "set the level of " + entityExpression.toString(e, debug) + " to " + numberExpression.toString(e, debug);
    }
}
