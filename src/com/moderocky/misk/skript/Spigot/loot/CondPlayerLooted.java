package com.moderocky.misk.skript.Spigot.loot;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.conditions.base.PropertyCondition.PropertyType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.destroystokyo.paper.loottable.LootableInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

/**
 * NOT IMPLEMENTED
 */

@Name("Has Player Looted")
@Description("Checks whether a player has looted a LootableInventory.")
@Examples("if player has metadata value \"healer\":")
@Since("0.1.8")
@SuppressWarnings("null")
public class CondPlayerLooted extends Condition {

    /*
    static {
        Skript.registerCondition(CondPlayerLooted.class,
                "%players% (has|have) looted %lootableinventory%",
                "%players% (has|have)( not|n't) looted %lootableinventory%"
        );
    }

     */

    @SuppressWarnings("null")
    private Expression<LootableInventory> inventory;
    @SuppressWarnings("null")
    private Expression<Player> players;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        players     = (Expression<Player>) exprs[0];
        inventory   = (Expression<LootableInventory>) exprs[1];
        setNegated(matchedPattern == 1);
        return true;
    }

    @Override
    public boolean check(Event e) {
        return inventory.check(e,
                inventory -> players.check(e,
                        inventory::hasPlayerLooted
                ), isNegated());
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return PropertyCondition.toString(this, PropertyType.HAVE, e, debug, players,
                "looted" + inventory.toString(e, debug));
    }
}
