package com.moderocky.misk.skript.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.moderocky.misk.effects.ClientSlotPacket;
import com.moderocky.misk.utils.SkriptUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;


@Name("Show Clientside Item")
@Description({
        "Show an entity as wearing an item.",
        "Slots are MAINHAND, HEAD, CHEST,",
        "LEGS, FEET and OFFHAND"})
@Examples({
        "show event-entity as wearing stone in slot \"HEAD\" for all players"
})
@Since("0.1.5")
public class EffShowClientSlot extends Effect {

    static {
        Skript.registerEffect(EffShowClientSlot.class, "show %entities% as wearing %itemtype% (on|in) [slot] %string% for %players%");
    }

    @SuppressWarnings("null")
    private Expression<Entity> entities;
    private Expression<Player> players;
    private Expression<ItemType> itemtype;
    private Expression<String> string;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        entities = (Expression<Entity>) exprs[0];
        itemtype = (Expression<ItemType>) exprs[1];
        string   = (Expression<String>) exprs[2];
        players  = (Expression<Player>) exprs[3];
        return true;
    }

    @Override
    protected void execute(Event event) {
        ItemStack item  = SkriptUtils.getItemStack(itemtype.getSingle(event));
        String slot     = string.getSingle(event);
        for (Player player : players.getArray(event)) {
            for (Entity entity : entities.getArray(event)) {
                ClientSlotPacket.showClientSlot(entity, player, slot, item);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "show " + entities.toString(e, debug) + " as wearing " + itemtype.toString(e, debug) + " (on|in) [slot] " + string.toString(e, debug) + " for " + players.toString(e, debug);
    }
}
