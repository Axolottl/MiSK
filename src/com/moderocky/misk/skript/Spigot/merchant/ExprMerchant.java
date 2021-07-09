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
import com.moderocky.misk.utils.MerchantUtils;
import org.bukkit.event.Event;
import org.bukkit.inventory.Merchant;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Name("New Merchant")
@Description({
        "Create a new custom merchant object."})
@Examples("set {_m} to a new merchant")
@Since("0.1.5")
public class ExprMerchant extends SimpleExpression<Merchant> {
    private Expression<String> name;

    static {
        Skript.registerExpression(ExprMerchant.class, Merchant.class, ExpressionType.SIMPLE,
                "[a] new merchant named %string%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.name = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected Merchant[] get(Event e) {
        List<Merchant> merchants    = new ArrayList<>();
        String title                = name.getSingle(e);
        Merchant trader             = MerchantUtils.merchant(title);
        merchants.add(trader);
        return merchants.toArray(new Merchant[0]);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Merchant> getReturnType() {
        return Merchant.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "a new merchant named " + name.toString(e, debug);
    }

}
