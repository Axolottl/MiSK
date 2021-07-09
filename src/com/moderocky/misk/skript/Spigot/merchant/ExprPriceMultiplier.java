package com.moderocky.misk.skript.Spigot.merchant;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.bukkit.inventory.MerchantRecipe;

import javax.annotation.Nullable;
import java.util.Arrays;

@Name("Trade Price Multiplier")
@Description({"The price multiplier of a trade."})
@Examples({"set the price multiplier of {_trade} to 1"})
@Since("0.3.0")
public class ExprPriceMultiplier extends PropertyExpression<MerchantRecipe, Integer> {

    static {
        Skript.registerExpression(ExprPriceMultiplier.class, Integer.class, ExpressionType.PROPERTY,
                "[the] price multiplier of %merchantrecipes%",
                "%merchantrecipes%'[s] price multiplier");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<? extends MerchantRecipe>) exprs[0]);
        return true;
    }

    @Override
    protected Integer[] get(Event e, MerchantRecipe[] recipes) {
        return Arrays
                .stream(recipes)
                .map(recipe -> recipe.getPriceMultiplier())
                .toArray(Integer[]::new);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the price multiplier of " + getExpr().toString(e, debug);
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    @Nullable
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD
                || mode == Changer.ChangeMode.REMOVE
                || mode == Changer.ChangeMode.SET
                || mode == Changer.ChangeMode.RESET)
            return CollectionUtils.array(Integer.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null)
            return;
        Integer changeValue = (Integer) delta[0];
        switch (mode) {
            case ADD:
                for (MerchantRecipe recipe : getExpr().getArray(e)) {
                    recipe.setPriceMultiplier(recipe.getMaxUses() + changeValue);
                }
                break;
            case REMOVE:
                for (MerchantRecipe recipe : getExpr().getArray(e)) {
                    recipe.setPriceMultiplier(recipe.getMaxUses() - changeValue);
                }
                break;
            case SET:
                for (MerchantRecipe recipe : getExpr().getArray(e)) {
                    recipe.setPriceMultiplier(changeValue);
                }
                break;
            case RESET:
                for (MerchantRecipe recipe : getExpr().getArray(e)) {
                    recipe.setPriceMultiplier(1);
                }
                break;
            default:
                assert false;
                break;
        }
    }
}
