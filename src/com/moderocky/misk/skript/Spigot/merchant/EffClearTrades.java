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
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.Event;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import javax.annotation.Nullable;


@Name("Clear Trades")
@Description("Clears the trades of a merchant/villager.")
@Examples({
        "clear trades of {thingy}"
})
@Since("0.5")
public class EffClearTrades extends Effect {

    static {
        Skript.registerEffect(EffClearTrades.class,
                "clear [(the|all)] (trade|recipe)[s] of [merchant] %merchant%",
                "clear [(the|all)] (trade|recipe)[s] of [(villager|[wandering ]trader)] %entity%"
        );
    }

    @SuppressWarnings("null")
    @Nullable
    private Expression<Merchant>        merchantExpression;
    @Nullable
    private Expression<Entity>          entityExpression;
    private Boolean                     entity;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entity = matchedPattern == 1;
        if (entity) { entityExpression = (Expression<Entity>) exprs[0]; } else { merchantExpression = (Expression<Merchant>) exprs[0]; }
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (entity && entityExpression.getSingle(e) != null) {
            if (entityExpression.getSingle(e) instanceof Villager) {
                Villager trader = (Villager) entityExpression.getSingle(e);
                MerchantUtils.clearRecipes(trader);
            } else if (entityExpression.getSingle(e) instanceof WanderingTrader) {
                WanderingTrader trader = (WanderingTrader) entityExpression.getSingle(e);
                MerchantUtils.clearRecipes(trader);
            }
        } else {
            Merchant trader = merchantExpression.getSingle(e);
            MerchantUtils.clearRecipes(trader);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        if (entity) {
            return "clear the trades of " + entityExpression.toString(e, debug);
        } else {
            return "clear the trades of " + merchantExpression.toString(e, debug);
        }
    }
}
