package com.moderocky.misk.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.moderocky.misk.enums.KeyType;
import com.moderocky.misk.events.PlayerVehicleSteer;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;


public class WASDProtocolListener implements Listener {

    private ProtocolManager     manager;
    private Plugin              plugin;
    private PacketSteerListener listener;

    public WASDProtocolListener(ProtocolManager manager, Plugin plugin) {
        this.manager    = manager;
        this.plugin     = plugin;
    }

    public void registerListener() {
        listener = new PacketSteerListener(plugin);
        manager.addPacketListener(listener);
    }

    public void cancelListener() {
        manager.removePacketListener(listener);
    }

    public class PacketSteerListener extends PacketAdapter {
        PluginManager   Server;
        Event           Event;
        BukkitScheduler Scheduler;

        PacketSteerListener(Plugin plugin) {
            super(plugin, ListenerPriority.HIGH, PacketType.Play.Client.STEER_VEHICLE);
        }

        @Override
        public synchronized void onPacketReceiving(final PacketEvent event) {

            if (event.getPacketType() == PacketType.Play.Client.STEER_VEHICLE) {
                boolean shift   = event.getPacket().getBooleans().read(1);
                boolean jump    = event.getPacket().getBooleans().read(0);
                float forward   = event.getPacket().getFloat().read(1);
                float sideways  = event.getPacket().getFloat().read(0);
                KeyType keyPressed;
                if (shift) keyPressed = KeyType.SNEAK;
                else if (jump) keyPressed = KeyType.JUMP;
                else if (forward > 0) keyPressed    = KeyType.FORWARDS;
                else if (forward < 0) keyPressed    = KeyType.BACKWARDS;
                else if (sideways > 0) keyPressed   = KeyType.LEFT;
                else if (sideways < 0) keyPressed   = KeyType.RIGHT;
                else keyPressed = KeyType.NONE;
                Server      = Bukkit.getServer().getPluginManager();
                Event       = new PlayerVehicleSteer(keyPressed, event.getPlayer());
                Scheduler   = Bukkit.getScheduler();

                Scheduler.runTask(plugin, () -> Server.callEvent(Event));
            }
        }
    }
}
