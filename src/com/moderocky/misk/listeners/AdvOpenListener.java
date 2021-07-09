package com.moderocky.misk.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.moderocky.misk.events.PlayerOpenAdvancements;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class AdvOpenListener implements Listener {

    private ProtocolManager manager;
    private Plugin plugin;
    private AdvOpenListener.PacketAdvancementsListener listener;

    public AdvOpenListener(ProtocolManager manager, Plugin plugin) {
        this.manager    = manager;
        this.plugin     = plugin;
    }

    public void registerListener() {
        listener = new PacketAdvancementsListener(plugin);
        manager.addPacketListener(listener);
    }

    public void cancelListener() {
        manager.removePacketListener(listener);
    }

    public class PacketAdvancementsListener extends PacketAdapter {
        PluginManager   Server;
        Event           Event;
        BukkitScheduler Scheduler;

        PacketAdvancementsListener(Plugin plugin) {
            super(plugin, ListenerPriority.HIGH, PacketType.Play.Client.ADVANCEMENTS);
        }

        @Override
        public synchronized void onPacketReceiving(final PacketEvent event) {

            if (event.getPacketType() == PacketType.Play.Client.ADVANCEMENTS) {
                Server      = Bukkit.getServer().getPluginManager();
                Event       = new PlayerOpenAdvancements(event.getPlayer());
                Scheduler   = Bukkit.getScheduler();
                Scheduler.runTask(plugin, () -> Server.callEvent(Event));
            }
        }
    }
}
