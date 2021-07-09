package com.moderocky.misk.skript.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.moderocky.misk.events.PlayerOpenAdvancements;
import org.bukkit.entity.Player;

public class EvtPlayerOpenAdv {

    static {
        Skript.registerEvent("Player Open Advancements", SimpleEvent.class, PlayerOpenAdvancements.class, "open [of] advancements [menu]")
                .description("Called when a player presses L or opens advancement menu.")
                .examples(
                        "on open advancements:",
                        "    cancel event")
                .since("1.0");
        EventValues.registerEventValue(PlayerOpenAdvancements.class, Player.class, new Getter<Player, PlayerOpenAdvancements>() {
            @Override
            public Player get(PlayerOpenAdvancements e) {
                return e.getPlayer();
            }
        }, 0);
    }
}
