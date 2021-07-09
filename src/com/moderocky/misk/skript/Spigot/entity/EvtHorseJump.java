package com.moderocky.misk.skript.Spigot.entity;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.HorseJumpEvent;

/**
 * No idea why Skript didn't have this already.
 *
 * @author Moderocky
 */

public class EvtHorseJump {
    static {
        Skript.registerEvent("Horse Jump", SimpleEvent.class, HorseJumpEvent.class, "horse jump[ing]")
                .description("Called when a horse jumps.")
                .examples(
                        "on horse jump:",
                        "	 # event-entity is the horsie",
                        "    # use passengers to find the rider.")
                .since("0.1.9");
        EventValues.registerEventValue(HorseJumpEvent.class, Entity.class, new Getter<Entity, HorseJumpEvent>() {
            @Override
            public Entity get(HorseJumpEvent e) {
                return e.getEntity();
            }
        }, 0);
        /*
            So I had a bad idea here...
            It theoretically works but I sensed many disapproving glances.
            I'll sneak it in later!

        EventValues.registerEventValue(HorseJumpEvent.class, Player.class, new Getter<Player, HorseJumpEvent>() {
            @Override
            public Player get(HorseJumpEvent e) {
                List list = e.getEntity().getPassengers();
                e.getEntity().getPassengers().get(0);
                if (e.getEntity().getPassengers().get(0) != null) {
                    // Check for first passenger
                    if (e.getEntity().getPassengers().get(0).getType() == EntityType.PLAYER) {
                        // Check player
                        Player player = (Player) e.getEntity().getPassengers().get(0);
                        if (player != null) {
                            // Well something could still fuck up
                            return player;
                        }
                    }
                }
                return null;
            }
        }, 0);

         */
    }
}
