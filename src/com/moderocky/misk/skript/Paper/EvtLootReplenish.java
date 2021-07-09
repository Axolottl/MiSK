package com.moderocky.misk.skript.Paper;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.destroystokyo.paper.loottable.LootableInventory;
import com.destroystokyo.paper.loottable.LootableInventoryReplenishEvent;
import org.bukkit.entity.Player;

public class EvtLootReplenish {
    static {
        Skript.registerEvent("Loot Replenish", SimpleEvent.class, LootableInventoryReplenishEvent.class, "loot re(plenish|fill)[ing]")
                .description("Called when a lootable inventory is refilled automatically.")
                .requiredPlugins("Paper 1.14+")
                .examples(
                        "on loot refill:",
                        "	 send message \"Yay! it worked!\" to player")
                .since("0.1.5");
        EventValues.registerEventValue(LootableInventoryReplenishEvent.class, Player.class, new Getter<Player, LootableInventoryReplenishEvent>() {
            @Override
            public Player get(LootableInventoryReplenishEvent e) {
                return e.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(LootableInventoryReplenishEvent.class, LootableInventory.class, new Getter<LootableInventory, LootableInventoryReplenishEvent>() {
            @Override
            public LootableInventory get(LootableInventoryReplenishEvent e) {
                return e.getInventory();
            }
        }, 0);
    }
}
