package com.moderocky.misk.skript.Spigot;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.loot.LootTable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Types {

    static {
        Classes.registerClass(new ClassInfo<>(Merchant.class, "merchant")
                .user("merchant?s?")
                .name("Merchant")
                .description("A custom merchant object used to store trades.")
                .since("0.1.5")
                .changer(new Changer<Merchant>() {
                             @Override
                             public Class<?>[] acceptChange(ChangeMode mode) {
                                 if (mode == ChangeMode.DELETE || mode == ChangeMode.RESET)
                                     return CollectionUtils.array();
                                 return null;
                             }

                             @Override
                             public void change(Merchant[] merchants, @Nullable Object[] delta, ChangeMode mode) {
                                 if (mode == ChangeMode.RESET) {
                                     for (Merchant merchant : merchants) {
                                         List empty = new ArrayList();
                                         merchant.setRecipes(empty);
                                     }
                                 }
                             }
                         }
                )
                .parser(new Parser<Merchant>() {
                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(Merchant merchant, int flags) {
                        return "merchant";
                    }

                    @Override
                    public String toVariableNameString(Merchant merchant) {
                        return "merchant";
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "\\S+";
                    }
                })
        );
        Classes.registerClass(new ClassInfo<>(MerchantRecipe.class, "merchantrecipe")
                .user("merchantrecipe?s?")
                .name("Merchant Recipe")
                .description("A custom trade.")
                .since("0.1.5")
            .parser(new Parser<MerchantRecipe>() {
                @Override
                public boolean canParse(ParseContext context) {
                    return false;
                }

                @Override
                public String toString(MerchantRecipe merchant, int flags) {
                    return "merchantrecipe";
                }

                @Override
                public String toVariableNameString(MerchantRecipe merchant) {
                    return "merchantrecipe";
                }

                @Override
                public String getVariableNamePattern() {
                    return "\\S+";
                }
            })
        );
        Classes.registerClass(new ClassInfo<>(LootTable.class, "loottable")
                .user("loottable?s?")
                .name("Loot Table")
                .description("A loot table, for example entity drops or dungeon chest loot.")
                .since("0.1.8")
                .parser(new Parser<LootTable>() {
                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(LootTable loottable, int flags) {
                        return "loottable";
                    }

                    @Override
                    public String toVariableNameString(LootTable loottable) {
                        return "loottable";
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "\\S+";
                    }
                })
        );
        /*
        Classes.registerClass(new ClassInfo<>(Lootable.class, "lootable")
                .user("lootable?s?")
                .name("Lootable Object")
                .description("A lootable object, for example an entity, chest, barrel, etc."
                + "\n NOT to be confused with a Loot Table.")
                .since("0.1.8")
                .parser(new Parser<Lootable>() {
                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(Lootable lootable, int flags) {
                        return "lootable";
                    }

                    @Override
                    public String toVariableNameString(Lootable lootable) {
                        return "lootable";
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "\\S+";
                    }
                })
        );

         */
    }
}
