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


@Name("Set Trades")
@Description("Sets the trades of a merchant.")
@Examples({
        "set trades of merchant {_m} to {_r::*}"
})
@Since("0.1.5")
public class EffSetTrades extends Effect {

    static {
        Skript.registerEffect(EffSetTrades.class,
                "set [(the|all)] (trade|recipe)[s] of [merchant] %merchant% to %merchantrecipes%",
                "set [(the|all)] (trade|recipe)[s] of [(villager|[wandering ]trader)] %entity% to %merchantrecipes%"
        );
    }

    @SuppressWarnings("null")
    @Nullable
    private Expression<Merchant> merchantExpression;
    @Nullable
    private Expression<Entity> entityExpression;
    private Boolean entity;
    private Expression<MerchantRecipe> merchantRecipeExpression;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entity = matchedPattern == 1;
        if (entity) {
            entityExpression = (Expression<Entity>) exprs[0];
        } else {
            merchantExpression = (Expression<Merchant>) exprs[0];
        }
        merchantRecipeExpression = (Expression<MerchantRecipe>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        MerchantRecipe[] trades = merchantRecipeExpression.getArray(e);
        if (entity && entityExpression.getSingle(e) != null) {
            if (entityExpression.getSingle(e) instanceof Villager) {
                Villager trader = (Villager) entityExpression.getSingle(e);
                MerchantUtils.setRecipes(trader, trades);
            } else if (entityExpression.getSingle(e) instanceof WanderingTrader) {
                WanderingTrader trader = (WanderingTrader) entityExpression.getSingle(e);
                MerchantUtils.setRecipes(trader, trades);
            }
        } else {
            Merchant trader = merchantExpression.getSingle(e);
            MerchantUtils.setRecipes(trader, trades);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        if (entity) {
            return "set the trades of " + entityExpression.toString(e, debug) + " to " + merchantRecipeExpression.toString(e, debug);
        } else {
            return "set the trades of " + merchantExpression.toString(e, debug) + " to " + merchantRecipeExpression.toString(e, debug);
        }
    }
}
