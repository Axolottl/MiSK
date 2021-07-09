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


@Name("Remove Trade")
@Description("Removes a trade from a merchant.")
@Examples({
        "remove trades {_r::*} from merchant {_m}"
})
@Since("0.2.5")
public class EffRemoveRecipe extends Effect {

    static {
        Skript.registerEffect(EffRemoveRecipe.class,
                "remove (trade|recipe)[s] %merchantrecipes% from [merchant] %merchant%",
                "remove (trade|recipe)[s] %merchantrecipes% from [(villager|[wandering ]trader)] %entity%"
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
            entityExpression = (Expression<Entity>) exprs[1];
        } else {
            merchantExpression = (Expression<Merchant>) exprs[1];
        }
        merchantRecipeExpression = (Expression<MerchantRecipe>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (entity) {
            if (entityExpression.getSingle(e) instanceof Villager) {
                Villager trader = (Villager) entityExpression.getSingle(e);
                for (MerchantRecipe recipe : merchantRecipeExpression.getArray(e)) {
                    MerchantUtils.removeRecipe(trader, recipe);
                }
            } else if (entityExpression.getSingle(e) instanceof WanderingTrader) {
                WanderingTrader trader = (WanderingTrader) entityExpression.getSingle(e);
                for (MerchantRecipe recipe : merchantRecipeExpression.getArray(e)) {
                    MerchantUtils.removeRecipe(trader, recipe);
                }
            }

        } else {
            Merchant trader = merchantExpression.getSingle(e);
            for (MerchantRecipe recipe : merchantRecipeExpression.getArray(e)) {
                MerchantUtils.removeRecipe(trader, recipe);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        if (entity) {
            return "remove trades " + merchantRecipeExpression.toString(e, debug) + " from " + entityExpression.toString(e, debug);
        } else {
            return "remove trades " + merchantRecipeExpression.toString(e, debug) + " from " + merchantExpression.toString(e, debug);
        }
    }
}
