package com.moderocky.misk.types;

import net.minecraft.server.v1_14_R1.IMerchant;
import net.minecraft.server.v1_14_R1.MerchantRecipeList;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftMerchantRecipe;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

/*
    I have something planned here, in the pipeline.
    It's not finished yet, though! :(

    Future Me, if you're reading this and you forgot...
    We were trying to make a simple method of accessing the NMS merchant constructor.
    This was to make the EXP bar/profession level malleable.

    If you're not Future Me, and you have any suggestions, please tell Future Me.
    If Future Me is dead or trapped in the Phantom Zone, and you're carrying on development,
    you might want to start over on this part.
 */

public class Merchant {

    protected final IMerchant merchant;

    public Merchant(IMerchant merchant) {
        this.merchant = merchant;
    }

    public IMerchant getMerchant() {
        return merchant;
    }

    public List<MerchantRecipe> getRecipes() {
        List<MerchantRecipe> recipes = new ArrayList<>();
        merchant.getOffers().toArray();
        for (Object recipe : merchant.getOffers().toArray()) {
            net.minecraft.server.v1_14_R1.MerchantRecipe recipe2 = (net.minecraft.server.v1_14_R1.MerchantRecipe) recipe;
            MerchantRecipe merchantRecipe = recipe2.asBukkit();
            recipes.add(merchantRecipe);
        }
        return recipes;
    }

    public void setRecipes(List<MerchantRecipe> recipes) {
        MerchantRecipeList recipesList = merchant.getOffers();
        recipesList.clear();
        for (MerchantRecipe recipe : recipes) {
            recipesList.add(CraftMerchantRecipe.fromBukkit(recipe).toMinecraft());
        }
    }

    public MerchantRecipeList getRecipeList() {
        return merchant.getOffers();
    }

    public MerchantRecipe getRecipe(int i) {
        return merchant.getOffers().get(i).asBukkit();
    }

    public void setRecipe(int i, MerchantRecipe merchantRecipe) {
        merchant.getOffers().set(i, CraftMerchantRecipe.fromBukkit(merchantRecipe).toMinecraft());
    }

    public int getRecipeCount() {
        return merchant.getOffers().size();
    }

    public CraftHumanEntity getPlayer() {
        CraftHumanEntity player = merchant.getTrader().getBukkitEntity();
        return player;
    }

}
