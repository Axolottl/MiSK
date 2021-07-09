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

@Name("Trade Uses")
@Description({"The current uses of a merchant recipe."})
@Examples({"set the uses of {_trade} to 0"})
@Since("0.3.0")
public class ExprUses extends PropertyExpression<MerchantRecipe, Number> {

    static {
        Skript.registerExpression(ExprUses.class, Number.class, ExpressionType.PROPERTY,
                "[the] use[s] of %merchantrecipes%",
                "%merchantrecipes%'[s] use[s]");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<? extends MerchantRecipe>) exprs[0]);
        return true;
    }

    @Override
    protected Number[] get(Event e, MerchantRecipe[] recipes) {
        return Arrays
                .stream(recipes)
                .map(recipe -> (Number) recipe.getUses())
                .toArray(Number[]::new);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the uses of " + getExpr().toString(e, debug);
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    @Nullable
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD
                || mode == Changer.ChangeMode.REMOVE
                || mode == Changer.ChangeMode.SET
                || mode == Changer.ChangeMode.RESET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null)
            return;
        Integer changeValue = ((Number) delta[0]).intValue();
        switch (mode) {
            case ADD:
                for (MerchantRecipe recipe : getExpr().getArray(e)) {
                    recipe.setUses(recipe.getMaxUses() + changeValue);
                }
                break;
            case REMOVE:
                for (MerchantRecipe recipe : getExpr().getArray(e)) {
                    recipe.setUses(recipe.getMaxUses() - changeValue);
                }
                break;
            case SET:
                for (MerchantRecipe recipe : getExpr().getArray(e)) {
                    recipe.setUses(changeValue);
                }
                break;
            case RESET:
                for (MerchantRecipe recipe : getExpr().getArray(e)) {
                    recipe.setUses(0);
                }
                break;
            default:
                assert false;
                break;
        }
    }
}
