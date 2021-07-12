package com.codingforcookies.armorequip;

import com.codingforcookies.armorequip.ArmorunEquipEvent.EquipMethod;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @Author Made by Borlea Fixed by Sharpjaws
 * @Github https://github.com/borlea/
 * @Website http://codingforcookies.com/
 * @since June 6, 2016 8:43:34 PM
 */
public class ArmorunEquipListener implements Listener {

    @EventHandler
    public final void onInventoryClick(final InventoryClickEvent e) {
        boolean shift = false, numberkey = false;
        if (e.isCancelled())
            return;
        if (e.getInventory().getType() != InventoryType.CREATIVE) {
            if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                shift = true;
            }
            if (e.getClick().equals(ClickType.NUMBER_KEY)) {
                numberkey = true;
            }
            EquipMethod method = EquipMethod.DRAG;
            if (ArmorType.matchType(e.getCurrentItem()) == null)
                return;
            ArmorType newArmorType = ArmorType.matchType(e.getCurrentItem());
            ArmorunEquipEvent armorunEquipEvent = new ArmorunEquipEvent((Player) e.getWhoClicked(), method,
                    newArmorType, e.getCurrentItem());
            if (e.getRawSlot() != newArmorType.getSlot())
                return;
            Bukkit.getServer().getPluginManager().callEvent(armorunEquipEvent);
            if (armorunEquipEvent.isCancelled()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void itemBreakEvent(PlayerItemBreakEvent e) {
        ArmorType type = ArmorType.matchType(e.getBrokenItem());
        if (type != null) {
            Player p = e.getPlayer();
            ArmorunEquipEvent ArmorunEquipEvent = new ArmorunEquipEvent(p, EquipMethod.BROKE, type, e.getBrokenItem());
            Bukkit.getServer().getPluginManager().callEvent(ArmorunEquipEvent);
            if (ArmorunEquipEvent.isCancelled()) {
                ItemStack i = e.getBrokenItem().clone();
                i.setAmount(1);
                i.setDurability((short) (i.getDurability() - 1));
                if (type.equals(ArmorType.HELMET)) {
                    p.getInventory().setHelmet(i);
                } else if (type.equals(ArmorType.CHESTPLATE)) {
                    p.getInventory().setChestplate(i);
                } else if (type.equals(ArmorType.LEGGINGS)) {
                    p.getInventory().setLeggings(i);
                } else if (type.equals(ArmorType.BOOTS)) {
                    p.getInventory().setBoots(i);
                }
            }
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent e) {
        Player p = e.getEntity();
        for (ItemStack i : p.getInventory().getArmorContents()) {
            if (i != null && !i.getType().equals(Material.AIR)) {
                Bukkit.getServer().getPluginManager()
                        .callEvent(new ArmorunEquipEvent(p, EquipMethod.DEATH, ArmorType.matchType(i), i));
                // No way to cancel a death event.
            }
        }
    }
}
