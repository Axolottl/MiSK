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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.Event;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import javax.annotation.Nullable;


@Name("Set Trade")
@Description({
        "Sets the trade of a merchant.",
        "Use this to REPLACE an existing trade.",
        "New trades must be added."})
@Examples({
        "set trade 1 of merchant {_m} to {_recipe}"
})
@Since("0.1.5")
public class EffSetTrade extends Effect {

    static {
        Skript.registerEffect(EffSetTrade.class, "set trade %number% of [merchant] %merchant% to %merchantrecipe%",
                "set trade %number% of [(villager|[wandering ]trader)] %entity% to %merchantrecipe%"
        );
    }

    @SuppressWarnings("null")
    @Nullable
    private Expression<Merchant> merchantExpression;
    @Nullable
    private Expression<Entity> entityExpression;
    private Boolean entity;
    private Expression<MerchantRecipe> merchantRecipeExpression;
    private Expression<Number> integer;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entity = matchedPattern == 1;
        if (entity) {
            entityExpression = (Expression<Entity>) exprs[1];
        } else {
            merchantExpression = (Expression<Merchant>) exprs[1];
        }
        integer = (Expression<Number>) exprs[0];
        merchantRecipeExpression = (Expression<MerchantRecipe>) exprs[2];
        return true;
    }

    @Override
    protected void execute(Event event) {
        MerchantRecipe recipe = merchantRecipeExpression.getSingle(event);
        int index = integer.getSingle(event).intValue();
        if (entity) {
            if (entityExpression.getSingle(event) instanceof Villager) {
                Villager trader = (Villager) entityExpression.getSingle(event);
                if (-1 < index && index < trader.getRecipeCount()) {
                    trader.setRecipe(index, recipe);
                }
            } else if (entityExpression.getSingle(event) instanceof WanderingTrader) {
                WanderingTrader trader = (WanderingTrader) entityExpression.getSingle(event);
                if (-1 < index && index < trader.getRecipeCount()) {
                    trader.setRecipe(index, recipe);
                }
            }
        } else {
            Merchant trader = merchantExpression.getSingle(event);
            if (-1 < index && index < trader.getRecipeCount()) {
                trader.setRecipe(index, recipe);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        if (entity) {
            return "set trade " + integer.toString(e, debug) + " of " + entityExpression.toString(e, debug) + " to " + merchantRecipeExpression.toString(e, debug);
        } else {
            return "set trade " + integer.toString(e, debug) + " of " + merchantExpression.toString(e, debug) + " to " + merchantRecipeExpression.toString(e, debug);
        }
    }
}
