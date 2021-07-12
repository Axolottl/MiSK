package com.codingforcookies.armorequip;

import com.codingforcookies.armorequip.ArmorEquipEvent.EquipMethod;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @Author Made by Borlea Fixed by Sharpjaws
 * @Github https://github.com/borlea/
 * @Website http://codingforcookies.com/
 * @since June 6, 2016 8:43:34 PM
 */
public class ArmorEquipListener implements Listener {

    @EventHandler
    public final void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClick().equals(ClickType.CREATIVE)) {
            EquipMethod method = EquipMethod.DRAG;
            if (ArmorType.matchType(e.getCursor()) == null)
                return;
            ArmorType newArmorType2 = ArmorType.matchType(e.getCurrentItem());
            if (!e.getWhoClicked().getItemOnCursor().equals(null) && e.getSlotType().equals(SlotType.CONTAINER)
                    || e.getSlotType().equals(SlotType.QUICKBAR))
                return;
            ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(), method, newArmorType2,
                    e.getCursor());
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                e.setCancelled(true);
            }
        } else {
            boolean shift = false, numberkey = false;
            if (e.isCancelled())
                return;
            if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                shift = true;
            }
            if (e.getClick().equals(ClickType.NUMBER_KEY)) {
                numberkey = true;
            }
            if ((e.getSlotType() != SlotType.ARMOR || e.getSlotType() != SlotType.QUICKBAR)
                    && !e.getInventory().getType().equals(InventoryType.CRAFTING))
                return;
            if (!(e.getWhoClicked() instanceof Player))
                return;
            if (e.getCurrentItem() == null)
                return;
            ArmorType newArmorType = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor());
            if (!shift && newArmorType != null && e.getRawSlot() != newArmorType.getSlot()) {
                // Used for drag and drop checking to make sure you aren't trying to place a
                // helmet in the boots place.
                return;
            }
            if (shift) {
                try {
                    if (!e.getClickedInventory().getType().equals(InventoryType.PLAYER))
                        return;
                } catch (NoSuchMethodError ignored) {

                }
                newArmorType = ArmorType.matchType(e.getCurrentItem());
                if (newArmorType != null) {
                    if (e.getSlotType() == SlotType.ARMOR)
                        return;
                    boolean equipping = true;
                    if (e.getRawSlot() == newArmorType.getSlot()) {
                        equipping = false;
                    }

                    if (newArmorType.equals(ArmorType.HELMET)
                            && (equipping == (e.getWhoClicked().getInventory().getHelmet() == null))
                            || newArmorType.equals(ArmorType.CHESTPLATE)
                            && (equipping == (e.getWhoClicked().getInventory().getChestplate() == null))
                            || newArmorType.equals(ArmorType.LEGGINGS)
                            && (equipping == (e.getWhoClicked().getInventory().getLeggings() == null))
                            || newArmorType.equals(ArmorType.BOOTS)
                            && (equipping == (e.getWhoClicked().getInventory().getBoots() == null))) {
                        ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(),
                                EquipMethod.SHIFT_CLICK, newArmorType, e.getCurrentItem());
                        Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                        if (armorEquipEvent.isCancelled()) {
                            Player p = (Player) e.getWhoClicked();
                            p.updateInventory();
                            e.setCancelled(true);

                        }
                    }
                }

            } else {
                ItemStack newArmorPiece = e.getCursor();
                ItemStack oldArmorPiece = e.getCurrentItem();
                if (numberkey) {
                    if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {// Prevents shit in the 2by2
                        // crafting
                        // e.getClickedInventory() == The players inventory
                        // e.getHotBarButton() == key people are pressing to equip or unequip the item
                        // to or from.
                        // e.getRawSlot() == The slot the item is going to.
                        // e.getSlot() == Armor slot, can't use e.getRawSlot() as that gives a hotbar
                        // slot ;-;
                        ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
                        if (hotbarItem != null) {// Equipping
                            newArmorType = ArmorType.matchType(hotbarItem);
                            newArmorPiece = hotbarItem;
                            oldArmorPiece = e.getClickedInventory().getItem(e.getSlot());
                        } else {
                            if (newArmorPiece.getType().equals(Material.AIR))
                                return;
                        }
                    }
                }
                if (newArmorType != null && e.getRawSlot() == newArmorType.getSlot()) {
                    EquipMethod method = EquipMethod.DRAG;
                    ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(), method,
                            newArmorType, e.getWhoClicked().getItemOnCursor());
                    if (e.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey)
                        method = EquipMethod.HOTBAR_SWAP;
                    if (e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.LEFT)) {
                        armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(), method, newArmorType,
                                e.getWhoClicked().getItemOnCursor());
                    } else if (e.getClick().equals(ClickType.NUMBER_KEY)) {
                        armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(), method, newArmorType,
                                newArmorPiece);
                    }
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    if (armorEquipEvent.isCancelled()) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL)
            return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (ArmorType.matchType(e.getItem()) == null)
                return;
            ArmorType newArmorType = ArmorType.matchType(e.getItem());
            if (newArmorType != null) {
                if (newArmorType.equals(ArmorType.HELMET) && e.getPlayer().getInventory().getHelmet() == null
                        || newArmorType.equals(ArmorType.CHESTPLATE)
                        && e.getPlayer().getInventory().getChestplate() == null
                        || newArmorType.equals(ArmorType.LEGGINGS) && e.getPlayer().getInventory().getLeggings() == null
                        || newArmorType.equals(ArmorType.BOOTS) && e.getPlayer().getInventory().getBoots() == null) {
                    if (e.getClickedBlock() == null || e.getClickedBlock() != null && !newArmorType.equals(null)) {
                        if (e.getClickedBlock() != null) {
                            if (e.getClickedBlock().getType().equals(Material.CHEST)
                                    || e.getClickedBlock().getType().equals(Material.DISPENSER)
                                    || e.getClickedBlock().getType().equals(Material.HOPPER)
                                    || e.getClickedBlock().getType().equals(Material.STORAGE_MINECART)
                                    || e.getClickedBlock().getType().equals(Material.HOPPER_MINECART)
                                    || e.getClickedBlock().getType().equals(Material.ENCHANTMENT_TABLE)
                                    || e.getClickedBlock().getType().equals(Material.FURNACE))
                                return;
                        }
                        ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(),
                                EquipMethod.HOTBAR, newArmorType, e.getItem());
                        Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                        if (armorEquipEvent.isCancelled()) {
                            e.setCancelled(true);
                            final Player player = e.getPlayer();
                            player.updateInventory();
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void dispenserFireEvent(BlockDispenseEvent e) {
        ArmorType type = ArmorType.matchType(e.getItem());
        if (e.getBlock() instanceof Dropper)
            return;
        if (ArmorType.matchType(e.getItem()) != null) {
            Location loc = e.getBlock().getLocation();
            for (Player p : loc.getWorld().getPlayers()) {
                if (loc.getBlockY() - p.getLocation().getBlockY() >= -1
                        && loc.getBlockY() - p.getLocation().getBlockY() <= 1) {
                    if (p.getInventory().getHelmet() == null && type.equals(ArmorType.HELMET)
                            || p.getInventory().getChestplate() == null && type.equals(ArmorType.CHESTPLATE)
                            || p.getInventory().getLeggings() == null && type.equals(ArmorType.LEGGINGS)
                            || p.getInventory().getBoots() == null && type.equals(ArmorType.BOOTS)) {
                        org.bukkit.block.Dispenser dispenser = (org.bukkit.block.Dispenser) e.getBlock().getState();
                        org.bukkit.material.Dispenser dis = (org.bukkit.material.Dispenser) dispenser.getData();
                        BlockFace directionFacing = dis.getFacing();
                        // Someone told me not to do big if checks because it's hard to read, look at me
                        // doing it -_-
                        if (directionFacing == BlockFace.EAST && p.getLocation().getBlockX() != loc.getBlockX()
                                && p.getLocation().getX() <= loc.getX() + 2.3 && p.getLocation().getX() >= loc.getX()
                                || directionFacing == BlockFace.WEST && p.getLocation().getX() >= loc.getX() - 1.3
                                && p.getLocation().getX() <= loc.getX()
                                || directionFacing == BlockFace.SOUTH && p.getLocation().getBlockZ() != loc.getBlockZ()
                                && p.getLocation().getZ() <= loc.getZ() + 2.3
                                && p.getLocation().getZ() >= loc.getZ()
                                || directionFacing == BlockFace.NORTH && p.getLocation().getZ() >= loc.getZ() - 1.3
                                && p.getLocation().getZ() <= loc.getZ()) {
                            ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, EquipMethod.DISPENSER,
                                    ArmorType.matchType(e.getItem()), e.getItem());
                            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                            if (armorEquipEvent.isCancelled()) {
                                e.setCancelled(true);
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void itemBreakEvent(PlayerItemBreakEvent e) {
        ArmorType type = ArmorType.matchType(e.getBrokenItem());
        if (type != null) {
            Player p = e.getPlayer();
            ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, EquipMethod.BROKE, type, e.getBrokenItem());
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
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
                ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, EquipMethod.DEATH, ArmorType.matchType(i), i);
                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                // No way to cancel a death event.
            }
        }
    }

    @EventHandler
    public void playerDragInventoryEvent(InventoryDragEvent e) {
        ArmorType newArmorType = ArmorType.matchType(e.getOldCursor());
        if (newArmorType == null)
            return;
        ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) e.getWhoClicked(), EquipMethod.DRAG,
                newArmorType, e.getOldCursor());
        if (!e.getRawSlots().contains(newArmorType.getSlot()))
            return;
        Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
        if (armorEquipEvent.isCancelled()) {
            e.setCancelled(true);
        }
    }
}
