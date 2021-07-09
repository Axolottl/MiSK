package com.moderocky.misk.skript.Spigot.merchant;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
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
import com.moderocky.misk.utils.SkriptUtils;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Name("New Merchant Recipe")
@Description({
        "Create a new trade."})
@Examples("set {_r} to a new trade of stone and stick for oak log")
@Since("0.1.5")
public class ExprMerchantRecipe extends SimpleExpression<MerchantRecipe> {

    @SuppressWarnings("null")
    private Expression<ItemType> itemTypeExpression1;
    private Expression<ItemType> itemTypeExpression2;
    private Expression<ItemType> itemTypeExpression3;

    static {
        Skript.registerExpression(ExprMerchantRecipe.class, MerchantRecipe.class, ExpressionType.SIMPLE,
                "[a] new (merchant recipe|trade) of %itemtype% and %itemtype% for %itemtype%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.itemTypeExpression1 = (Expression<ItemType>) exprs[0];
        this.itemTypeExpression2 = (Expression<ItemType>) exprs[1];
        this.itemTypeExpression3 = (Expression<ItemType>) exprs[2];
        return true;
    }

    @Override
    protected MerchantRecipe[] get(Event e) {
        List<MerchantRecipe> merchantRecipes = new ArrayList<>();
        ItemStack item1 = SkriptUtils.getItemStack(itemTypeExpression1.getSingle(e));
        ItemStack item2 = SkriptUtils.getItemStack(itemTypeExpression2.getSingle(e));
        ItemStack item3 = SkriptUtils.getItemStack(itemTypeExpression3.getSingle(e));
        merchantRecipes.add(MerchantUtils.tradeCreator(item1, item2, item3));
        return merchantRecipes.toArray(new MerchantRecipe[0]);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends MerchantRecipe> getReturnType() {
        return MerchantRecipe.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "new trade of " + itemTypeExpression1.toString(e, debug) + " and " + itemTypeExpression2.toString(e, debug) + " for " + itemTypeExpression3.toString(e, debug);
    }

}
