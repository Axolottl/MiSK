package com.moderocky.misk.utils;

import net.minecraft.server.v1_14_R1.VillageSiege;
import net.minecraft.server.v1_14_R1.WorldServer;
import org.bukkit.entity.Player;

/**
 * I would like to make more out of Villages.
 * Sadly, they're ill-defined and badly managed in NMS.
 *
 * A lot of it seems to be guesswork. (Is there a village here? Who knows.)
 *
 * @author Moderocky
 */

public class VillageUtils {
    public static void beginSiege(Player player) {
        VillageSiege siege = new VillageSiege((WorldServer) player.getLocation().getWorld());
        siege.a();
    }
}
