package com.moderocky.misk.skript.Spigot.merchant;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.Merchant;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Name("Merchant Recipe Count")
@Description({
        "Get the number of trades of a merchant"})
@Examples("set {_n} to the trade count of merchant {bob}")
@Since("0.1.5")
public class ExprMerchantRecipeCount extends SimpleExpression<Number> {
    private Expression<Merchant> merchant;

    static {
        Skript.registerExpression(ExprMerchantRecipeCount.class, Number.class, ExpressionType.SIMPLE,
                "[the] (recipe|trade) count of [merchant] %merchant%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.merchant = (Expression<Merchant>) exprs[0];
        return true;
    }

    @Override
    protected Number[] get(Event e) {
        List<Number> integers  = new ArrayList<>();
        Merchant trader        = merchant.getSingle(e);
        integers.add(trader.getRecipeCount());
        return integers.toArray(new Number[0]);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the trade count of merchant " + merchant.toString(e, debug);
    }

}
