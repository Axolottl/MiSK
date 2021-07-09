package com.moderocky.misk.effects;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.moderocky.misk.MiSK;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

public class ClientSlotPacket {

    // Not finished, not really sure what I'm doing here :(
    // Possible method change in 1.14?

    private static EnumWrappers.ItemSlot slot;
    private static Boolean                 sendPacket;

    public static void showClientSlot(Entity entity, Player player, String string, ItemStack item) {

        if (string == "MAINHAND" || string == "HAND") {
            slot           = EnumWrappers.ItemSlot.MAINHAND;
            sendPacket     = true;
        }
        else if (string == "OFFHAND" || string == "LEFTHAND") {
            slot           = EnumWrappers.ItemSlot.OFFHAND;
            sendPacket     = true;

        }
        else if (string == "HEAD" || string == "HELMET") {
            slot           = EnumWrappers.ItemSlot.HEAD;
            sendPacket     = true;

        }
        else if (string == "CHEST" || string == "CHESTPLATE") {
            slot           = EnumWrappers.ItemSlot.CHEST;
            sendPacket     = true;
        }
        else if (string == "LEGS" || string == "LEGGINGS") {
            slot           = EnumWrappers.ItemSlot.LEGS;
            sendPacket     = true;
        }
        else if (string == "FEET" || string == "BOOTS") {
            slot           = EnumWrappers.ItemSlot.FEET;
            sendPacket     = true;
        }

        if (sendPacket) {
            ProtocolManager manager     = MiSK.getInstance().getProtocol();
            PacketContainer packet      = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);

            packet.getItemSlots().write(0, slot);
            packet.getIntegers().write(0, entity.getEntityId());
            packet.getItemModifier().write(0, item);
            try {
                manager.sendServerPacket(player, packet);
            } catch (InvocationTargetException exception) {
                exception.printStackTrace();
            }
        }
    }
}
