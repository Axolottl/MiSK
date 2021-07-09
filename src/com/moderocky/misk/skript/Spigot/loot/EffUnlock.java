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


@Name("Unlock Container")
@Description({
        "Unlock a container.",
        "This will allow it to be opened."
})
@Examples({
        "unlock target block"
})
@Since("0.1.8")

public class EffUnlock extends Effect {

    static {
        Skript.registerEffect(EffUnlock.class, "unlock %blocks%");
    }

    @SuppressWarnings("null")
    private Expression<Block> blocks;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        blocks = (Expression<Block>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        Block[] block = blocks.getArray(e);
        for (Block lockable : block) {
            LootTableUtils.unlock(lockable);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "unlock " + blocks.toString(e, debug);
    }
}
