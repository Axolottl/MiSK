package com.moderocky.misk.skript.Spigot.entity;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.moderocky.misk.utils.EntityUtils;
import org.bukkit.entity.Entity;

@Name("Is in Water")
@Description("Checks whether an entity is in water. This includes waterlogged blocks, and partially submerged.")
@Examples("target entity is in water")
@Since("0.2.5")
public class CondIsInWater extends PropertyCondition<Entity> {

    static {
        register(CondIsInWater.class, "in water", "entities");
    }

    @Override
    public boolean check(final Entity entity) {
        if (EntityUtils.isInWater(entity)) {
            return true;
        }
        assert false;
        return false;
    }

    @Override
    protected String getPropertyName() {
        return "in water";
    }
}
