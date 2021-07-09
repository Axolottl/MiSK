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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.Event;
import org.bukkit.inventory.Merchant;

import javax.annotation.Nullable;


@Name("Open Merchant")
@Description("Opens a merchant to players." +
        "\n  - Can be used to open the same merchant to multiple players." +
        "\n  - Careful, this might break stuff." +
        "\n  - This can technically be used to open Wandering Traders as well.")
@Examples({
        "open merchant {_m} to player",
        "open merchant {auction-list} to all players"
})
@Since("0.1.5")
public class EffOpenMerchant extends Effect {

    static {
        Skript.registerEffect(EffOpenMerchant.class,
                "open merchant %merchant% to %players%",
                "open (villager|[wandering ]trader) %entity% to %players%");
    }

    @SuppressWarnings("null")
    @Nullable
    private Expression<Merchant> merchantExpression;
    @Nullable
    private Expression<Entity> entityExpression;
    private Boolean entity;
    private Expression<Player> playerExpression;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entity = matchedPattern == 1;
        if (entity) {
            entityExpression = (Expression<Entity>) exprs[0];
        } else {
            merchantExpression = (Expression<Merchant>) exprs[0];
        }
        playerExpression = (Expression<Player>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (entity) {
            if (entityExpression.getSingle(e) instanceof Villager) {
                Villager trader = (Villager) entityExpression.getSingle(e);
                for (Player player : playerExpression.getArray(e)) {
                    player.openMerchant(trader, true);
                }
            } else if (entityExpression.getSingle(e) instanceof WanderingTrader) {
                WanderingTrader trader = (WanderingTrader) entityExpression.getSingle(e);
                for (Player player : playerExpression.getArray(e)) {
                    player.openMerchant(trader, true);
                }
            }
        } else {
            Merchant trader = merchantExpression.getSingle(e);
            for (Player player : playerExpression.getArray(e)) {
                player.openMerchant(trader, true);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        if (entity) {
            return "open " + entityExpression.toString(e, debug) + " to " + playerExpression.toString(e, debug);
        } else {
            return "open " + merchantExpression.toString(e, debug) + " to " + playerExpression.toString(e, debug);
        }
    }
}
