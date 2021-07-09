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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Name("Merchant Recipe Count")
@Description({
        "Get the number of trades of a merchant"})
@Examples("set {_n} to the trade count of merchant {bob}")
@Since("0.1.5")
public class ExprEntityRecipeCount extends SimpleExpression<Number> {
    private Expression<Entity> entity;

    static {
        Skript.registerExpression(ExprEntityRecipeCount.class, Number.class, ExpressionType.SIMPLE,
                "[the] (recipe|trade) count of [villager] %entity%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.entity = (Expression<Entity>) exprs[0];
        return true;
    }

    @Override
    protected Number[] get(Event e) {
        List<Number> integers = new ArrayList<>();
        if (entity.getSingle(e) instanceof Villager) {
            Villager trader = (Villager) entity.getSingle(e);
            integers.add(trader.getRecipeCount());
            return integers.toArray(new Number[0]);
        } else if (entity.getSingle(e) instanceof WanderingTrader) {
            WanderingTrader trader  = (WanderingTrader) entity.getSingle(e);
            integers.add(trader.getRecipeCount());
            return integers.toArray(new Number[0]);
        } else {
            return null;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the trade count of villager " + entity.toString(e, debug);
    }

}
