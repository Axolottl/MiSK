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


@Name("Add Trade")
@Description("Adds a trade to a merchant/villager/trader.")
@Examples({
        "add trades {_r::*} to merchant {Merchant-Mike}",
        "add trades {trade-list::*} to target entity",
        "add trades {_t::*} to villager {victor}"
})
@Since("0.1.5")
public class EffAddRecipe extends Effect {

    static {
        Skript.registerEffect(EffAddRecipe.class,
                "add (trade|recipe)[s] %merchantrecipes% to [merchant] %merchant%",
                "add (trade|recipe)[s] %merchantrecipes% to [(villager|[wandering ]trader)] %entity%");
    }

    @SuppressWarnings("null")
    @Nullable
    private Expression<Merchant> merchantExpression;
    @Nullable
    private Expression<Entity> entityExpression;
    private Expression<MerchantRecipe> merchantRecipeExpression;
    private Boolean entity;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entity = matchedPattern == 1;
        merchantRecipeExpression = (Expression<MerchantRecipe>) exprs[0];
        if (entity) {
            entityExpression = (Expression<Entity>) exprs[1];
        } else {
            merchantExpression = (Expression<Merchant>) exprs[1];
        }
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (entity) {
            if (entityExpression.getSingle(e) instanceof Villager) {
                Villager trader = (Villager) entityExpression.getSingle(e);
                for (MerchantRecipe recipe : merchantRecipeExpression.getArray(e)) {
                    MerchantUtils.addRecipe(trader, recipe);
                }
            } else if (entityExpression.getSingle(e) instanceof WanderingTrader) {
                WanderingTrader trader = (WanderingTrader) entityExpression.getSingle(e);
                for (MerchantRecipe recipe : merchantRecipeExpression.getArray(e)) {
                    MerchantUtils.addRecipe(trader, recipe);
                }
            }

        } else {
            Merchant trader = merchantExpression.getSingle(e);
            for (MerchantRecipe recipe : merchantRecipeExpression.getArray(e)) {
                MerchantUtils.addRecipe(trader, recipe);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        if (entity) {
            return "add trades " + merchantRecipeExpression.toString(e, debug) + " to " + entityExpression.toString(e, debug);
        } else {
            return "add trades " + merchantRecipeExpression.toString(e, debug) + " to " + merchantExpression.toString(e, debug);
        }
    }
}
