package com.moderocky.misk.utils;

import net.minecraft.server.v1_14_R1.IMerchant;
import net.minecraft.server.v1_14_R1.MerchantRecipe;
import net.minecraft.server.v1_14_R1.MerchantRecipeList;
import net.minecraft.server.v1_14_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftMerchant;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftMerchantRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *   This is basically a set of utilities for converting between NMS/OBC/Bukkit.
 *   I'm not 100% sure what it's useful for yet.
 *   I had planned something for NMS recipes, but who knows?
 */

public class MerchantNMS {

    public static net.minecraft.server.v1_14_R1.ItemStack item1;
    public static net.minecraft.server.v1_14_R1.ItemStack item2;
    public static net.minecraft.server.v1_14_R1.ItemStack item3;
    public static MerchantRecipe recipe;

    public static String getVersion() {
        String ver = Bukkit.getServer().getClass().getPackage().getName();
        return ver.substring(ver.lastIndexOf(".") + 1);
    }

    public static Class<?> getOBCClass(String className) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getNMSClass(String className) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MerchantRecipe getNMSTrade(ItemStack itemStack1, ItemStack itemStack2, ItemStack itemStack3) {
        item1 = CraftItemStack.asNMSCopy(itemStack1);
        item2 = CraftItemStack.asNMSCopy(itemStack2);
        item3 = CraftItemStack.asNMSCopy(itemStack3);
        return new MerchantRecipe(item1, item2, item3, 0, 99999, 0, 1);
    }

    public static ItemStack getResult(MerchantRecipe recipe) {
        return CraftItemStack.asBukkitCopy(recipe.getSellingItem());
    }

    public static List getIngredients(MerchantRecipe recipe) {
        List<ItemStack> ingredients = new ArrayList();
        item1 = recipe.getBuyItem1();
        item2 = recipe.getBuyItem2();
        item3 = recipe.getSellingItem();
        ingredients.add(CraftItemStack.asBukkitCopy(item1));
        ingredients.add(CraftItemStack.asBukkitCopy(item2));
        ingredients.add(CraftItemStack.asBukkitCopy(item3));
        return ingredients;
    }

    public static MerchantRecipeList getRecipeList(List recipeList) {
        return new MerchantRecipeList();
    }

    public static void addToList(MerchantRecipeList merchantRecipes, MerchantRecipe merchantRecipe) {
        merchantRecipes.add(merchantRecipe);
    }
    public static void addToList(MerchantRecipeList merchantRecipes, MerchantRecipeList newList) {
        Collection<MerchantRecipe> merchantRecipeCollection = newList;
        merchantRecipes.addAll(merchantRecipeCollection);
    }

    /**
     * This method actually runs the trade, I believe.
     * @param merchantRecipes The recipelist, from the villager.
     * @param itemStack1 The first ingredient.
     * @param itemStack2 The second ingredient.
     * @param index The index of the recipe.
     * @return The recipe, I presume? I'm not sure.
     */
    public static MerchantRecipe executeTrade(MerchantRecipeList merchantRecipes, ItemStack itemStack1, ItemStack itemStack2, Integer index) {
        item1 = CraftItemStack.asNMSCopy(itemStack1);
        item2 = CraftItemStack.asNMSCopy(itemStack2);
        return merchantRecipes.a(item1, item2, index);
    }


    public static IMerchant asNMSCopy(org.bukkit.inventory.Merchant merchant) {
        CraftMerchant merchant1 = (CraftMerchant) merchant;
        merchant1.getMerchant();
        return  merchant1.getMerchant();
    }

    public static World getNMSWorld(Merchant merchant) {
        return MerchantNMS.asNMSCopy(merchant).getWorld();
    }

    public static CraftMerchant asCraftCopy(Merchant merchant) {
        return MerchantNMS.asNMSCopy(merchant).getCraftMerchant();
    }

    public static org.bukkit.inventory.MerchantRecipe asBukkitCopy(MerchantRecipe recipe) {
        return recipe.asBukkit();
    }
    public static org.bukkit.inventory.MerchantRecipe asBukkitCopy(CraftMerchantRecipe recipe) {
        return recipe.toMinecraft().asBukkit();
    }
    public static CraftMerchantRecipe asCraftCopy(org.bukkit.inventory.MerchantRecipe recipe) {
        return CraftMerchantRecipe.fromBukkit(recipe);
    }
    public static MerchantRecipe asNMSCopy(CraftMerchantRecipe recipe) {
        return recipe.toMinecraft();
    }
    public static MerchantRecipe asNMSCopy(org.bukkit.inventory.MerchantRecipe recipe) {
        CraftMerchantRecipe bukkit  = CraftMerchantRecipe.fromBukkit(recipe);
        int     uses                = recipe.getUses();
        int     maxUses             = recipe.getMaxUses();
        int     experience          = recipe.getVillagerExperience();
        float   priceMultiplier     = recipe.getPriceMultiplier();
        item1 = CraftItemStack.asNMSCopy(recipe.getIngredients().get(0));
        item2 = CraftItemStack.asNMSCopy(recipe.getIngredients().get(1)); // should be ItemStack.a == air?
        item3 = CraftItemStack.asNMSCopy(recipe.getResult());
        return new MerchantRecipe(item1, item2, item3, uses, maxUses, experience, priceMultiplier, bukkit);
    }

    public static void increaseUses(MerchantRecipe recipe) {
        recipe.increaseUses();
        // The heck is this supposed to be?
    }
}
