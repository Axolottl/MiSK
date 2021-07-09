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


@Name("Set Trade Experience")
@Description(
        {"Set the villager experience of a trade.",
        "Player experience is separate."})
@Examples({
        "set exp of trade {_r} to 5"
})
@Since("0.1.5")
public class EffSetVillagerExp extends Effect {

    static {
        Skript.registerEffect(EffSetVillagerExp.class, "set [e]xp[erience] of (trade|recipe) %merchantrecipe% to %integer%");
    }

    @SuppressWarnings("null")
    private Expression<MerchantRecipe>  merchantrecipe;
    private Expression<Integer>         integer;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        merchantrecipe  = (Expression<MerchantRecipe>)  exprs[0];
        integer         = (Expression<Integer>)         exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        Integer         exp     = integer.getSingle(e);
        MerchantRecipe  recipe  = merchantrecipe.getSingle(e);
        MerchantUtils.setExp(recipe, exp);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "set exp of trade "  + merchantrecipe.toString(e, debug) + " to " + integer.toString(e, debug);
    }
}
