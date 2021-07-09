package com.moderocky.misk.skript.Spigot.loot;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.moderocky.misk.utils.LootTableUtils;
import org.bukkit.block.Block;

@Name("Is Locked")
@Description("Checks whether a container is locked.")
@Examples("target block is locked")
@Since("0.1.8")
public class CondIsLocked extends PropertyCondition<Block> {

    static {
        register(CondIsLocked.class, "locked", "blocks");
    }

    @Override
    public boolean check(final Block block) {
        if (LootTableUtils.isLocked(block)) {
            return true;
        }
        assert false;
        return false;
    }

    @Override
    protected String getPropertyName() {
        return "locked";
    }
}
