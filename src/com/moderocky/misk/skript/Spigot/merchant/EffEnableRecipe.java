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
import org.bukkit.inventory.MerchantRecipe;

import javax.annotation.Nullable;


@Name("Enable Trade")
@Description("Enables a merchant recipe object.")
@Examples({
        "enable {_recipes::*}"
})
@Since("0.3.5")
public class EffEnableRecipe extends Effect {

    static {
        Skript.registerEffect(EffEnableRecipe.class, "enable %merchantrecipes%");
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
            MerchantUtils.enableRecipe(recipe);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "enable " + merchantRecipeExpression.toString(e, debug);
    }
}
