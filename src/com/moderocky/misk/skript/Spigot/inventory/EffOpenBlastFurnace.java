package com.moderocky.misk.skript.Spigot.inventory;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nullable;


/**
 * Simple blast furnace inventory creation for 1.14+
 *
 * @author Moderocky
 */

@Name("Open Blast Furnace Inventory")
@Description({
        "Open a blast furnace inventory."})
@Examples("open blast furnace named \"Bob\" to all players")
@Since("0.2.0")
public class EffOpenBlastFurnace extends Effect {
    private Expression<String> stringExpression;
    private Expression<Player> playerExpression;

    static {
        Skript.registerEffect(EffOpenBlastFurnace.class, "open [fake] blast furnace named %string% to %players%");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, SkriptParser.ParseResult result) {
        stringExpression = (Expression<String>) expr[0];
        playerExpression = (Expression<Player>) expr[1];
        return true;
    }

    /**
     * "Woah woah hold up Rocky, why are we creating individual inventories?"
     * Ah, I'm glad you asked!
     *
     * You see, in 1.14+, inventories work a little differently.
     * Unless we're in an event, it's quite hard to actually get any values from it.
     *
     * This way, we can use getHolder() and then work from there!
     *
     * Also, it's a far better practice to create individuals, if we're going to be messing with them.
     */
    @SuppressWarnings("null")
    @Override
    protected void execute(Event event) {
        Player[] players = playerExpression.getArray(event);
        String string = stringExpression.getSingle(event);
        for (Player player : players) {
            Inventory inventory = Bukkit.createInventory(player, InventoryType.BLAST_FURNACE, string);
            player.openInventory(inventory);
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "open blast furnace named " + stringExpression.toString(event, debug) + " to " + playerExpression.toString(event, debug);
    }
}
