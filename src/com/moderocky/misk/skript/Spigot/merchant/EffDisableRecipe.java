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
import org.bukkit.event.Event;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import javax.annotation.Nullable;


@Name("Disable Trade")
@Description("Disables a merchant recipe object.")
@Examples({
        "disable {_recipes::*}"
})
@Since("0.3.5")
public class EffDisableRecipe extends Effect {

    static {
        Skript.registerEffect(EffDisableRecipe.class, "disable %merchantrecipes%");
    }

    @SuppressWarnings("null")
    private Expression<MerchantRecipe> merchantRecipeExpression;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        merchantRecipeExpression  = (Expression<MerchantRecipe>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        MerchantRecipe[] merchantRecipes = merchantRecipeExpression.getArray(e);
        for (MerchantRecipe recipe : merchantRecipes) {
            MerchantUtils.disableRecipe(recipe);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "disable " + merchantRecipeExpression.toString(e, debug);
    }
}
