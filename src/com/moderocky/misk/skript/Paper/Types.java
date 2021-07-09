package com.moderocky.misk.skript.Paper;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.destroystokyo.paper.loottable.LootableInventory;

public class Types {

    static {
        Classes.registerClass(new ClassInfo<>(LootableInventory.class, "lootableinventory")
                .user("lootableinventory")
                .name("Lootable Inventory")
                .description("An inventory with a loot-table. For example, a loot chest.")
                .since("0.1.8")
                .parser(new Parser<LootableInventory>() {
                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(LootableInventory lootableinventory, int flags) {
                        return "lootableinventory";
                    }

                    @Override
                    public String toVariableNameString(LootableInventory lootableinventory) {
                        return "lootableinventory";
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "\\S+";
                    }
                })
        );
    }
}
