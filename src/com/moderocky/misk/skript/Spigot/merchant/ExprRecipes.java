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
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Name("Recipes")
@Description({"The recipes of a merchant."})
@Examples({"set {_r::*} to the recipes of merchant {_m}"})
@Since("0.3.0")
public class ExprRecipes extends PropertyExpression<Merchant, MerchantRecipe> {

    static {
        Skript.registerExpression(ExprRecipes.class, MerchantRecipe.class, ExpressionType.PROPERTY,
                "[the] (recipe|trade)[s] of %merchant%",
                "%merchant%'[s] (recipe|trade)[s]");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<? extends Merchant>) exprs[0]);
        return true;
    }

    @Override
    protected MerchantRecipe[] get(Event e, Merchant[] merchants) {
        List<MerchantRecipe> recipeList = new ArrayList<>();
        for (Merchant merchant : merchants) {
            recipeList.addAll(merchant.getRecipes());
        }
        return recipeList.toArray(new MerchantRecipe[0]);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the trades of " + getExpr().toString(e, debug);
    }

    @Override
    public Class<? extends MerchantRecipe> getReturnType() {
        return MerchantRecipe.class;
    }

    @Override
    @Nullable
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return null;
    }
}
