package com.moderocky.misk.utils;

import net.minecraft.server.v1_14_R1.MerchantRecipeList;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 *
 *  What's this?
 *  It's MerchantUtils!!!
 *
 *  Some of this is actually quite useful. It simplifies the trade creation process, especially for CMOs.
 *  However, some bits you might think are weird.
 *
 *  Q: Why do some of these just call the Bukkit method?
 *  A: I'm going to convert entirely to using IMerchant soon. Then they're gonna get super complex.
 *  Anybody using the Bukkit methods would then no longer be able to use them on the NMS merchants.
 *  The Bukkit methods don't allow access to some key parts.
 *  Methods in this class are basically guaranteed to always work.
 *
 *  @author Moderocky
 *
 */

public class MerchantUtils {

    private static  Merchant merchant;
    private static  MerchantRecipe recipe;
    static TreeMap<String, Merchant>     traderList;

    private static  List<MerchantRecipe> recipes;

    private static  ItemStack item1;
    private static  ItemStack item2;

    private static  List recipeList;
    private static  HumanEntity player;

    /**
     * The crown jewel of my entire collection...
     * A simplified version of the Bukkit method!
     *
     * NMS has a nice method like this.
     * Bukkit has some crap with 50 parameters and no ingredients.
     *
     * @param item1 First item
     * @param item2 Second item
     * @param item3 Result item
     * @return A 'safe' recipe, which can be used a lot and doesn't give experience.
     */
    public static MerchantRecipe tradeCreator(ItemStack item1, ItemStack item2, ItemStack item3) {
        recipe = new MerchantRecipe(item3, 99999);
        recipe.setVillagerExperience(0);
        recipe.setPriceMultiplier(1);
        recipe.addIngredient(item1);
        recipe.addIngredient(item2);
        return recipe;
    }

    /**
     * @param recipe The recipe we wanna modify.
     * @return The same recipe, with 0 remaining uses. It can't restock, either.
     */
    public static MerchantRecipe disableRecipe(MerchantRecipe recipe) {
        recipe.setMaxUses(0);
        return recipe;
    }

    /**
     * @param recipe The recipe we wanna modify.
     * @return The same recipe, with 99999 remaining uses..
     */
    public static MerchantRecipe enableRecipe(MerchantRecipe recipe) {
        recipe.setMaxUses(99999);
        return recipe;
    }

    /**
     * It's a surprise tool that will help us later!
     * @return A list.
     */
    public static List tradeList() {
        recipes = new ArrayList<MerchantRecipe>();
        return recipes;
    }

    /**
     * A list based on existing recipes.
     * @param recipes A collection of recipes. Maybe converted from NMS MerchantRecipeList?
     * @return A list with them in.
     */
    public static List tradeList(MerchantRecipe[] recipes) {
        recipeList = new ArrayList<MerchantRecipe>();
        recipeList.add(recipes);
        return recipeList;
    }

    public static void addToList(MerchantRecipe recipe, List list) {
        list.add(recipe);
    }

    /**
     * Sets the recipelist to an empty list.
     * @param merchant A CMO (Custom Merchant Object).
     */
    public static void clearRecipes(Merchant merchant) {
        List trades = new ArrayList<MerchantRecipe>();
        merchant.setRecipes(trades);
    }

    /**
     * Remove a singular recipe. Useful if you want to iterate a list and do it.
     * @param merchant A CMO (Custom Merchant Object).
     * @param recipe The recipe you wanna remove.
     */
    public static void removeRecipe(Merchant merchant, MerchantRecipe recipe) {
        List trades = new ArrayList<MerchantRecipe>();
        if (merchant.getRecipeCount() > 0) {
            trades.addAll(merchant.getRecipes());
            trades.remove(recipe);
            merchant.setRecipes(trades);
        }
        merchant.setRecipes(trades);
    }

    /**
     * Remove a collection of recipes.
     * @param merchant A CMO (Custom Merchant Object).
     * @param recipes The recipe you wanna remove.
     */
    public static void removeRecipes(Merchant merchant, MerchantRecipe[] recipes) {
        List trades = new ArrayList<MerchantRecipe>();
        if (merchant.getRecipeCount() > 0) {
            trades.addAll(merchant.getRecipes());
            trades.remove(recipes);
            merchant.setRecipes(trades);
        }
        merchant.setRecipes(trades);
    }

    /**
     * The following set recipes to singular or plural recipes.
     */
    public static void setRecipes(Merchant merchant, MerchantRecipe recipe) {
        List trades = new ArrayList<MerchantRecipe>();
        trades.add(recipe);
        merchant.setRecipes(trades);
    }
    public static void setRecipes(Merchant merchant, MerchantRecipe[] recipes) {
        List trades = new ArrayList<MerchantRecipe>();
        for (MerchantRecipe recipe : recipes){
            trades.add(recipe);
        }
        merchant.setRecipes(trades);
    }

    public static void setType(Villager villager, Villager.Type type) {
        villager.setVillagerType(type);
    }

    public static void setType(Villager villager, String type) {
        try {
            villager.setVillagerType(Villager.Type.valueOf(type));
        } catch (Exception exception) {
            // Illegal type.
        }
    }

    public static void setProfession(Villager villager, Villager.Profession profession) {
        villager.setProfession(profession);
    }

    public static void setProfession(Villager villager, String profession) {
        try {
            villager.setProfession(Villager.Profession.valueOf(profession));
        } catch (Exception exception) {
            // Illegal profession.
        }
    }

    public static void setLevel(Villager villager, Integer level) {
        if (level < 1) {
            villager.setVillagerLevel(1);
        } else if (level > 5) {
            villager.setVillagerLevel(5);
        } else {
            villager.setVillagerLevel(level);
        }
    }

    public static void setLevel(Villager villager, Number level) {
        if (level.intValue() < 1) {
            villager.setVillagerLevel(1);
        } else if (level.intValue() > 5) {
            villager.setVillagerLevel(5);
        } else {
            villager.setVillagerLevel(level.intValue());
        }
    }

    public static void setExperience(Villager villager, Number amount) {
        villager.setVillagerExperience(amount.intValue());
    }


    /**
     * Adding recipes.
     */
    public static void addRecipe(Merchant merchant, MerchantRecipe recipe) {
        List trades = new ArrayList<MerchantRecipe>();
        if (merchant.getRecipeCount() > 0) {
            trades.addAll(merchant.getRecipes());
        }
        trades.add(recipe);
        merchant.setRecipes(trades);
    }
    public static void addRecipe(Merchant merchant, MerchantRecipe[] recipes) {
        List trades = new ArrayList<MerchantRecipe>();
        if (merchant.getRecipeCount() > 0) {
            trades.addAll(merchant.getRecipes());
        }
        for (MerchantRecipe recipe : recipes) {
            trades.add(recipe);
        }
        merchant.setRecipes(trades);
    }

    public static void setExp(MerchantRecipe recipe, Integer experience) {
        recipe.setVillagerExperience(experience);
    }

    /**
     * Now, this one is a bit of fun!
     * Useful for cloning recipes, it allows you to transfer the usage stats from one to another.
     */
    public static void transferStats(MerchantRecipe oldRecipe, MerchantRecipe recipe) {
        recipe.setVillagerExperience(oldRecipe.getVillagerExperience());
        recipe.setPriceMultiplier(oldRecipe.getPriceMultiplier());
        recipe.setMaxUses(oldRecipe.getMaxUses());
        recipe.setUses(oldRecipe.getUses());
        recipe.setExperienceReward(oldRecipe.hasExperienceReward());
    }

    /**
     * Bukkit doesn't have this. Rokkit does. :)
     * @param oldRecipe The original recipe.
     * @param item3 Result item.
     * @return A new recipe with the stats of the old.
     */
    public static MerchantRecipe recipeWithResult(MerchantRecipe oldRecipe, ItemStack item3) {
        item1   = oldRecipe.getIngredients().get(0);
        item2   = oldRecipe.getIngredients().get(1);
        recipe  = tradeCreator(item1, item2, item3);
        transferStats(oldRecipe, recipe);
        return recipe;
    }

    public static MerchantRecipe cloneRecipe(MerchantRecipe oldRecipe) {
        ItemStack result = oldRecipe.getResult();
        return recipeWithResult(oldRecipe, result);
    }

    public static void increaseUses(MerchantRecipe recipe) {
        MerchantNMS.asNMSCopy(recipe).increaseUses();
    }

    public static Boolean isDepleted(MerchantRecipe recipe) {
        return MerchantNMS.asNMSCopy(recipe).isFullyUsed();
    }

    public static MerchantRecipeList getMerchantRecipeList(Merchant merchant) {
        return MerchantNMS.asNMSCopy(merchant).getOffers();
    }

    /**
     * Merchant + Trader + Villager methods
     */

    public static void enableRecipe(Merchant merchant, Integer index) {
        recipe = MerchantUtils.getRecipe(merchant, index);
        recipe.setMaxUses(99999);
        merchant.setRecipe(index, recipe);
    }
    public static void enableRecipe(WanderingTrader merchant, Integer index) {
        recipe = MerchantUtils.getRecipe(merchant, index);
        recipe.setMaxUses(99999);
        merchant.setRecipe(index, recipe);
    }
    public static void enableRecipe(Villager merchant, Integer index) {
        recipe = MerchantUtils.getRecipe(merchant, index);
        recipe.setMaxUses(99999);
        merchant.setRecipe(index, recipe);
    }

    public void disableRecipe(Merchant merchant, Integer index) {
        recipe = MerchantUtils.getRecipe(merchant, index);
        recipe.setMaxUses(0);
        merchant.setRecipe(index, recipe);
    }
    public void disableRecipe(WanderingTrader merchant, Integer index) {
        recipe = MerchantUtils.getRecipe(merchant, index);
        recipe.setMaxUses(0);
        merchant.setRecipe(index, recipe);
    }
    public void disableRecipe(Villager merchant, Integer index) {
        recipe = MerchantUtils.getRecipe(merchant, index);
        recipe.setMaxUses(0);
        merchant.setRecipe(index, recipe);
    }

    public static MerchantRecipe getRecipe(Merchant merchant, Integer index) {
        recipe = merchant.getRecipe(index);
        return recipe;
    }
    public static MerchantRecipe getRecipe(WanderingTrader merchant, Integer index) {
        recipe = merchant.getRecipe(index);
        return recipe;
    }
    public static MerchantRecipe getRecipe(Villager merchant, Integer index) {
        recipe = merchant.getRecipe(index);
        return recipe;
    }

    public static MerchantRecipe recipeWithResult(Merchant merchant, Integer index, ItemStack result) {
        recipe = merchant.getRecipe(index);
        recipe = recipeWithResult(recipe, result);
        return recipe;
    }
    public static MerchantRecipe recipeWithResult(WanderingTrader merchant, Integer index, ItemStack result) {
        recipe = merchant.getRecipe(index);
        recipe = recipeWithResult(recipe, result);
        return recipe;
    }
    public static MerchantRecipe recipeWithResult(Villager merchant, Integer index, ItemStack result) {
        recipe = merchant.getRecipe(index);
        recipe = recipeWithResult(recipe, result);
        return recipe;
    }

    public static void setResult(Merchant merchant, Integer index, ItemStack result) {
        recipe = recipeWithResult(merchant, index, result);
        merchant.setRecipe(index, recipe);
    }
    public static void setResult(WanderingTrader merchant, Integer index, ItemStack result) {
        recipe = recipeWithResult(merchant, index, result);
        merchant.setRecipe(index, recipe);
    }
    public static void setResult(Villager merchant, Integer index, ItemStack result) {
        recipe = recipeWithResult(merchant, index, result);
        merchant.setRecipe(index, recipe);
    }

    public static HumanEntity getTrader(Merchant merchant) {
        player = merchant.getTrader();
        return player;
    }
    public static HumanEntity getTrader(WanderingTrader merchant) {
        player = merchant.getTrader();
        return player;
    }
    public static HumanEntity getTrader(Villager merchant) {
        player = merchant.getTrader();
        return player;
    }

    public static List getTradeList(Merchant merchant) {
        recipeList = merchant.getRecipes();
        return recipeList;
    }
    public static List getTradeList(WanderingTrader merchant) {
        recipeList = merchant.getRecipes();
        return recipeList;
    }
    public static List getTradeList(Villager merchant) {
        recipeList = merchant.getRecipes();
        return recipeList;
    }

    public static void setTradeList(Merchant merchant, List trades) {
        merchant.setRecipes(trades);
    }
    public static void setTradeList(WanderingTrader merchant, List trades) {
        merchant.setRecipes(trades);
    }
    public static void setTradeList(Villager merchant, List trades) {
        merchant.setRecipes(trades);
    }


    public static Integer getLevel(Villager villager) {
        return villager.getVillagerLevel();
    }
    public static Integer getLevel(WanderingTrader villager) {
        return 0;
        // No level, but then I can just call the method without worrying :)
    }
    public static Integer getExperience(Villager villager) {
        return villager.getVillagerExperience();
    }
    public static Integer getExperience(WanderingTrader villager) {
        return 0;
        // See above :)
    }
    public static void setExperience(Villager villager, Integer integer) {
        villager.setVillagerExperience(integer);
    }
    public static void setExperience(WanderingTrader villager, Integer integer) {
        // What is this?
        // It's for when some doofus tries to call the method with a wandering trader.
        // Trust me, it's gonna happen.
    }

    /**
     * Merchant creators
     */

    public static Merchant merchant() {
        merchant = Bukkit.createMerchant("Merchant");
        return merchant;
    }
    public static Merchant merchant(String title) {
        merchant = Bukkit.createMerchant(title);
        return merchant;
    }
    public static Merchant merchant(List recipes) {
        merchant = Bukkit.createMerchant("Merchant");
        merchant.setRecipes(recipes);
        return merchant;
    }
    public static Merchant merchant(String title, List recipes) {
        merchant = Bukkit.createMerchant(title);
        merchant.setRecipes(recipes);
        return merchant;
    }
    public static Merchant merchant(String title, MerchantRecipe[] recipes) {
        merchant    = Bukkit.createMerchant(title);
        List trades = new ArrayList<MerchantRecipe>();
        for (MerchantRecipe recipe : recipes) {
            trades.add(recipe);
        }
        merchant.setRecipes(trades);
        return merchant;
    }
    public static Merchant merchant(MerchantRecipe[] recipes) {
        merchant    = Bukkit.createMerchant("Merchant");
        List trades = new ArrayList<MerchantRecipe>();
        for (MerchantRecipe recipe : recipes) {
            trades.add(recipe);
        }
        merchant.setRecipes(trades);
        return merchant;
    }

    /**
     * Skript ID things
     */

    public static void setup() {
        traderList = new TreeMap<String, Merchant>();
    }
    public static void createMerchant(String id, String name) {
        merchant = Bukkit.createMerchant(name);
        traderList.put(id, merchant);
    }
    public static void deleteMerchant(String id) {
        traderList.remove(id);
    }
    public static void saveMerchant(String id, Merchant merchant) {
        traderList.put(id, merchant);
    }
    public static TreeMap getIDList() {
        return traderList;
    }
    public static Merchant merchantFromID(String id) {
        return traderList.get(id);
    }

    /**
     * Merchant openers.
     *
     * @param player The player
     * @param merchant The merchant object
     */

    public static void openMerchant(Player player, Merchant merchant) {
        player.openMerchant(merchant, true);
    }
    public static void openMerchant(Player player, WanderingTrader merchant) {
        player.openMerchant(merchant, false);
    }
    public static void openMerchant(Player player, Villager merchant) {
        player.openMerchant(merchant, false);
    }
}
