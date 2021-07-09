package com.moderocky.misk.events;

import com.moderocky.misk.enums.KeyType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerVehicleSteer extends Event {

    private static final HandlerList handlers = new HandlerList();

    private KeyType     keyPressed;
    private Player      player;

    public PlayerVehicleSteer(KeyType keyPressed, Player player) {
        this.keyPressed = keyPressed;
        this.player     = player;
    }

    public KeyType getKeyPressed() {
        return keyPressed;
    }

    public Player getPlayer() {
        return player;
    }

    public String getKeyName() {
        return keyPressed.getKeyname();
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}