package com.moderocky.misk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.moderocky.misk.listeners.AdvOpenListener;
import com.moderocky.misk.listeners.WASDProtocolListener;
import com.moderocky.misk.skript.Spigot.misc.EffDrawLaser;
import com.moderocky.misk.utils.MerchantUtils;
import com.moderocky.misk.utils.NMSInterface;
import com.moderocky.misk.utils.ReflectionUtils;
import com.moderocky.misk.utils.nms.v1_14_R1;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * MiSK - A Miscellaneous Addon
 * @author Moderocky
 *
 * This addon contains random things.
 * It also has some updated bits from abandoned addons.
 *
 * Credit List:
 *
 * TheBukor     - The original SkStuff pathfinding
 * QuickScythe  - Some help with NMS things
 * JoshK_       - Fixed my IntelliJ like 6+ times
 *
 */

public class MiSK extends JavaPlugin {
    private static  MiSK            instance;
    private         Logger          log;
                    PluginManager   pluginManager;
                    SkriptAddon     addon;
    private         ProtocolManager protocolManager;
    private         String          packageName     = "com.moderocky.misk";
    private static  String          pluginName      = "MiSK";
    private static  NMSInterface    nmsMethods;


    /**
     * Here we register our ProtocolLib listeners, if it's present.
     * If not, we can ignore it. That's fine.
     *
     * We also register our Skript syntax.
     *
     * And then we run init() to get started.
     */

    @Override
    public void onEnable() {
        log        =   getLogger();
        instance   =   this;
        skript();
        init();
    }

    @Override
    public void onDisable() {
        instance    =   null;
        log         =   getLogger();
        log.info("*  MiSK disabled!");
    }

    /**
     * We set up our NMS thingies.
     */

    private void skript() {
        log        =   getLogger();
        instance   =   this;
        if ((Bukkit.getPluginManager().getPlugin("Skript") != null) && (Skript.isAcceptRegistrations())) {
            addon = Skript.registerAddon(instance);
            try {
                if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
                    protocolManager = ProtocolLibrary.getProtocolManager();
                    new WASDProtocolListener(protocolManager, instance).registerListener();
                    new AdvOpenListener(protocolManager, instance).registerListener();
                    addon.loadClasses(getPackageName(), "skript.ProtocolLib");
                    log.info("*  ProtocolLib has been linked!");
                }
                else {
                    log.info("*  ProtocolLib has not been found!");
                }
                addon.loadClasses(getPackageName(), "skript.Paper");
                addon.loadClasses(getPackageName(), "skript.Spigot");
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("*  Skript has been linked!");
        }
        else {
            log.info("*  Skript was not found.");
            log.info("*  Are you confused, perhaps?");
            disablePlugin();

        }
    }

    private void init() {
        log.info("*  MiSK enabled! A plugin by Moderocky.");
        MerchantUtils.setup();
        setupNMSVersion();
        // everybody else seems to use configs... I don't like configs D:
    }

    private boolean setupNMSVersion() {
        String version = ReflectionUtils.getVersion();
        if (version.equals("v1_14_R0.")) {
            nmsMethods = new v1_14_R1();
            getLogger().info("It looks like you're deficient! Please update to 1.14.1.");
        } else if (version.equals("v1_14_R1.") || version.equals("v1_14_R2.")) {
            nmsMethods = new v1_14_R1();
            getServer().getConsoleSender().sendMessage("Oh good, you're actually using a supported version.");
        } else {
            getLogger().warning("It looks like we need to learn to read. This version isn't supported.");
        }
        return nmsMethods != null;
    }

    public static NMSInterface getNMSMethods() {
        return nmsMethods;
    }

    public boolean supported() {
        String version = ReflectionUtils.getVersion();
        if (version.equals("v1_14_R0.") || version.equals("v1_14_R1.")) {
            /*
                Todo: make a supported version collection.
             */
            return true;
        }
        return false;
    }

    private void disablePlugin() {
        pluginManager.disablePlugin(this);
    }

    public static MiSK getInstance() {
        return instance;
    }

    public ProtocolManager getProtocol() {
        return protocolManager;
    }

    public String getPackageName() {
        return packageName;
    }

    public static String getPluginName() {
        return pluginName;
    }

}