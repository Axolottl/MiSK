package com.moderocky.misk.skript.Spigot.loot;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.destroystokyo.paper.loottable.LootableInventory;

/**
 * NOT IMPLEMENTED
 */

@Name("Is Filled")
@Description("Whether a LootableInventory has been filled.")
@Examples("{_i} is filled")
@Since("0.1.8")
public class CondIsFilled extends PropertyCondition<LootableInventory> {

    /*
    static {
        register(CondIsFilled.class,"filled", "players");
    }

     */

    @Override
    public boolean check(LootableInventory inventory) {
        return inventory.hasBeenFilled();
    }

    @Override
    protected String getPropertyName() {
        return "filled";
    }
}
