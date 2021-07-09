package com.moderocky.misk.skript.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.moderocky.misk.events.PlayerVehicleSteer;
import org.bukkit.entity.Player;

public class EvtVehicleSteer {

    static {
        Skript.registerEvent("Vehicle Steer", SimpleEvent.class, PlayerVehicleSteer.class, "vehicle steer[ing]")
                .description("Called on every vehicle move packet. Strings are WASD, SHIFT and SPACEBAR. If no key is pressed, the event-string will be \"NONE\".")
                .examples(
                        "on vehicle steer:",
                        "    if player's vehicle is a pig:",
                        "        broadcast \"It's a pig!\" # this will get called a lot. Please don't do this...")
                .requiredPlugins("ProtocolLib 1.4+")
                .since("1.0");
        EventValues.registerEventValue(PlayerVehicleSteer.class, Player.class, new Getter<Player, PlayerVehicleSteer>() {
            @Override
            public Player get(PlayerVehicleSteer e) {
                return e.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(PlayerVehicleSteer.class, String.class, new Getter<String, PlayerVehicleSteer>() {
            @Override
            public String get(PlayerVehicleSteer e) {
                return e.getKeyName();
            }
        }, 0);
    }
}
