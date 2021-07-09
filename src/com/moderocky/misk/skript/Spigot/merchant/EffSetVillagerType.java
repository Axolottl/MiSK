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
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.Event;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import javax.annotation.Nullable;


@Name("Set Villager Type")
@Description("Sets the type of a villager.")
@Examples({
        "set type of villager {_v} to \"PLAINS\"",
        "set type of villager {_v} to \"SWAMP\"",
        "set type of villager {_v} to \"JUNGLE\"",
        "set type of villager {_v} to \"SAVANNA\"",
        "set type of villager {_v} to \"DESERT\"",
        "set type of villager {_v} to \"SNOW\"",
        "set type of villager {_v} to \"TAIGA\""
})
@Since("1.0.0")
public class EffSetVillagerType extends Effect {

    static {
        Skript.registerEffect(EffSetVillagerType.class,
                "set [the] type of [villager] %entities% to %string%"
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
                MerchantUtils.setType(trader, stringExpression.getSingle(e));
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "set the type of " + entityExpression.toString(e, debug) + " to " + stringExpression.toString(e, debug);
    }
}
