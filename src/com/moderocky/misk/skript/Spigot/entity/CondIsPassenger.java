package com.moderocky.misk.skript.Spigot.entity;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.moderocky.misk.utils.EntityUtils;
import org.bukkit.entity.Entity;

@Name("Is a Passenger")
@Description("Checks whether an entity is a passenger. ")
@Examples("target entity is a passenger")
@Since("0.2.5")
public class CondIsPassenger extends PropertyCondition<Entity> {

    static {
        register(CondIsPassenger.class, "[a] passenger[s]", "entities");
    }

    @Override
    public boolean check(final Entity entity) {
        if (EntityUtils.isPassenger(entity)) {
            return true;
        }
        assert false;
        return false;
    }

    @Override
    protected String getPropertyName() {
        return "a passenger";
    }
}
