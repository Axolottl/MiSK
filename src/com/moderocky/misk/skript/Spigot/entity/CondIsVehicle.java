package com.moderocky.misk.skript.Spigot.entity;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.moderocky.misk.utils.EntityUtils;
import org.bukkit.entity.Entity;

@Name("Is a Vehicle")
@Description("Checks whether an entity is a legal vehicle. ")
@Examples("target entity is a vehicle")
@Since("0.2.5")
public class CondIsVehicle extends PropertyCondition<Entity> {

    static {
        register(CondIsVehicle.class, "[a] vehicle", "entities");
    }

    @Override
    public boolean check(final Entity entity) {
        if (EntityUtils.isVehicle(entity)) {
            return true;
        }
        assert false;
        return false;
    }

    @Override
    protected String getPropertyName() {
        return "a vehicle";
    }
}
