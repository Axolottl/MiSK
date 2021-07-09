package com.moderocky.misk.skript.Spigot.loot;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.moderocky.misk.utils.LootTableUtils;
import org.bukkit.block.Block;
import org.bukkit.event.Event;

import javax.annotation.Nullable;


@Name("Lock Container")
@Description({
        "Lock a container, using a text key.",
        "The name of an item functions as the key to open it."
})
@Examples({
        "lock target block with key \"test\""
})
@Since("0.1.8")

public class EffLock extends Effect {

    static {
        Skript.registerEffect(EffLock.class, "lock %blocks% with [key] %string%");
    }

    @SuppressWarnings("null")
    private Expression<Block>  blocks;
    private Expression<String> string;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        blocks  = (Expression<Block>) exprs[0];
        string  = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        String key      = string.getSingle(e);
        Block[] block   = blocks.getArray(e);
        for (Block lockable : block) {
            LootTableUtils.lock(lockable, key);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "lock " + blocks.toString(e, debug) + " with key "+ string.toString(e, debug);
    }
}
