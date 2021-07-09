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


@Name("Set Villager Profession")
@Description("Sets the profession of a villager.")
@Examples({
        "set profession of villager {_v} to \"FARMER\""
})
@Since("1.0.0")
public class EffSetVillagerProfession extends Effect {

    static {
        Skript.registerEffect(EffSetVillagerProfession.class,
                "set [the] (prof[ession]|job) of [villager] %entities% to %string%"
        );
    }

    @SuppressWarnings("null")
    private Expression<Entity>          entityExpression;
    private Expression<String>          stringExpression;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entityExpression = (Expression<Entity>) exprs[0];
        stringExpression = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        for (Entity entity : entityExpression.getArray(e)) {
            if (entity instanceof Villager) {
                Villager trader = (Villager) entity;
                MerchantUtils.setProfession(trader, stringExpression.getSingle(e));
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "set the profession of " + entityExpression.toString(e, debug) + " to " + stringExpression.toString(e, debug);
    }
}
