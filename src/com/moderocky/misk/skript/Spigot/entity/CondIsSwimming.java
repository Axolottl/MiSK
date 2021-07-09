package com.moderocky.misk.skript.Spigot.entity;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.moderocky.misk.utils.EntityUtils;
import org.bukkit.entity.Entity;

@Name("Is Swimming")
@Description("Checks whether an entity is swimming. I presume this refers to the 1.13+ swimming style.")
@Examples("target entity is swimming")
@Since("0.2.5")
public class CondIsSwimming extends PropertyCondition<Entity> {

    static {
        register(CondIsSwimming.class, "swimming", "entities");
    }

    @Override
    public boolean check(final Entity entity) {
        if (EntityUtils.isSwimming(entity)) {
            return true;
        }
        assert false;
        return false;
    }

    @Override
    protected String getPropertyName() {
        return "swimming";
    }
}
