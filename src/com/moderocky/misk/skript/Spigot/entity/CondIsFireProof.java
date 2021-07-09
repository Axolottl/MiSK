package com.moderocky.misk.skript.Spigot.entity;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import com.moderocky.misk.utils.EntityUtils;
import org.bukkit.entity.Entity;

@Name("Is Fireproof")
@Description("Checks whether an entity can be damaged by fire. Nether entities and things with fire resistance can't be." +
        "This only counts default Minecraft behaviour. If you have some strange plugin or system, it might miss it!")
@Examples("target entity is fireproof")
@Since("0.2.5")
public class CondIsFireProof extends PropertyCondition<Entity> {

    static {
        register(CondIsFireProof.class, "fire[ ]proof[ed]", "entities");
    }

    @Override
    public boolean check(final Entity entity) {
        if (EntityUtils.isFireProof(entity)) {
            return true;
        }
        assert false;
        return false;
    }

    @Override
    protected String getPropertyName() {
        return "fireproof";
    }
}
