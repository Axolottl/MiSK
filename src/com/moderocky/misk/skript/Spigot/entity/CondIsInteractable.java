package com.moderocky.misk.skript.Spigot.entity;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.moderocky.misk.utils.EntityUtils;
import org.bukkit.entity.Entity;

@Name("Is Interactable")
@Description("Checks whether an entity can be interacted with, in some way.")
@Examples("target entity is interactable")
@Since("0.2.5")
public class CondIsInteractable extends PropertyCondition<Entity> {

    static {
        register(CondIsInteractable.class, "interactable", "entities");
    }

    @Override
    public boolean check(final Entity entity) {
        if (EntityUtils.isInteractable(entity)) {
            return true;
        }
        assert false;
        return false;
    }

    @Override
    protected String getPropertyName() {
        return "interactable";
    }
}
