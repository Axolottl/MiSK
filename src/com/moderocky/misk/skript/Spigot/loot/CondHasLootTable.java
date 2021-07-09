package com.moderocky.misk.skript.Spigot.loot;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.moderocky.misk.utils.LootTableUtils;
import org.bukkit.event.Event;
import org.bukkit.loot.Lootable;

import javax.annotation.Nullable;

/**
 * NOT IMPLEMENTED
 */

@Name("Has Loot Table")
@Description("Whether a lootable object (container/entity) has a table assigned to it.")
@Examples("{_e} has a loot table")
@Since("0.1.8")
public class CondHasLootTable extends Condition {
    /*

    static {
        Skript.registerCondition(CondPlayerLooted.class,
                "%object% has [a] loot[ ]table",
                "%object% does( not|n't) have [a] loot[ ]table"
        );
    }

     */

    @SuppressWarnings("null")
    private Expression<Object> object;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        object = (Expression<Object>) exprs[0];
        setNegated(matchedPattern == 1);
        return true;
    }

    @Override
    public boolean check(Event e) {
        Lootable lootable = (Lootable) object.getSingle(e);
        return LootTableUtils.hasLootTable(lootable);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return PropertyCondition.toString(this, PropertyCondition.PropertyType.HAVE, e, debug, object,
                "has loot table");
    }
}
