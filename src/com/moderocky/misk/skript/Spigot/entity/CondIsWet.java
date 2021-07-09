package com.moderocky.misk.skript.Spigot.entity;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.moderocky.misk.utils.EntityUtils;
import org.bukkit.entity.Entity;

@Name("Is Wet")
@Description("Checks whether an entity is in water, or rain. Useful to check if players can use their riptide/channelling enchantments!")
@Examples("target entity is wet")
@Since("0.2.5")
public class CondIsWet extends PropertyCondition<Entity> {

    static {
        register(CondIsWet.class, "wet", "entities");
    }

    @Override
    public boolean check(final Entity entity) {
        if (EntityUtils.isWet(entity)) {
            return true;
        }
        assert false;
        return false;
    }

    @Override
    protected String getPropertyName() {
        return "wet";
    }
}
