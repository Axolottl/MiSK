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


@Name("Enable Trade")
@Description("Enable a trade.")
@Examples({
        "enable trade 0 of merchant {_merchy}"
})
@Since("0.1.5")
public class EffEnableIndex extends Effect {

    static {
        Skript.registerEffect(EffEnableIndex.class,
                "enable (trade|recipe) %number% of [merchant] %merchant%",
                "enable (trade|recipe) %number% of [(villager|[wandering ]trader)] %entity%"
        );
    }

    @SuppressWarnings("null")
    @Nullable
    private Expression<Merchant> merchantExpression;
    @Nullable
    private Expression<Entity>   entityExpression;
    private Boolean              entity;
    private Expression<Number>   number;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entity = matchedPattern == 1;
        if (entity) { entityExpression = (Expression<Entity>) exprs[1]; } else { merchantExpression = (Expression<Merchant>) exprs[1]; }
        number  = (Expression<Number>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (entity) {
            if (entityExpression.getSingle(e) instanceof Villager) {
                Villager trader = (Villager) entityExpression.getSingle(e);
                if (-1 < number.getSingle(e).intValue() && number.getSingle(e).intValue() < trader.getRecipeCount()) {
                    MerchantRecipe recipe = MerchantUtils.getRecipe(trader, number.getSingle(e).intValue());
                    MerchantUtils.enableRecipe(recipe);
                    trader.setRecipe(number.getSingle(e).intValue(), recipe);
                }
            } else if (entityExpression.getSingle(e) instanceof WanderingTrader) {
                WanderingTrader trader = (WanderingTrader) entityExpression.getSingle(e);
                if (-1 < number.getSingle(e).intValue() && number.getSingle(e).intValue() < trader.getRecipeCount()) {
                    MerchantRecipe recipe = MerchantUtils.getRecipe(trader, number.getSingle(e).intValue());
                    MerchantUtils.enableRecipe(recipe);
                    trader.setRecipe(number.getSingle(e).intValue(), recipe);
                }
            }
        } else {
            Merchant trader = merchantExpression.getSingle(e);
            if (-1 < number.getSingle(e).intValue() && number.getSingle(e).intValue() < trader.getRecipeCount()) {
                MerchantRecipe recipe = MerchantUtils.getRecipe(trader, number.getSingle(e).intValue());
                MerchantUtils.enableRecipe(recipe);
                trader.setRecipe(number.getSingle(e).intValue(), recipe);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        if (entity) {
            return "enable trade " + number.toString(e, debug) + " of " + entityExpression.toString(e, debug);
        } else {
            return "enable trade " + number.toString(e, debug) + " of " + merchantExpression.toString(e, debug);
        }
    }
}
