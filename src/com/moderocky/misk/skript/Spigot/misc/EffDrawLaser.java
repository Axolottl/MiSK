package com.moderocky.misk.skript.Spigot.misc;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.moderocky.misk.MiSK;
import com.moderocky.misk.utils.Laser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.MissingFormatArgumentException;

@Name("Draw Laser")
@Description("Draw a client-side guardian laser.")
@Examples({
        "draw laser from player to target block for 5 seconds"
})
@Since("0.2.5")
public class EffDrawLaser extends Effect {

    static {
        Skript.registerEffect(EffDrawLaser.class, "draw (guardian beam|laser) from %location% to %location% [[and] keep] for %integer% second[s]");
    }

    @SuppressWarnings("null")
    private Expression<Location> location1;
    private Expression<Location> location2;
    private Expression<Integer>  integer;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        location1   = (Expression<Location>) exprs[0];
        location2   = (Expression<Location>) exprs[1];
        integer     = (Expression<Integer>) exprs[2];
        return true;
    }

    @Override
    protected void execute(Event e) {
        Location from = location1.getSingle(e);
        Location to   = location2.getSingle(e);
        Integer time  = integer.getSingle(e);
        if (from.getWorld() == to.getWorld() && from.distance(to) < 128) {
            // Not sure what the limit actually is on distance, apparently it's 15m but I doubt this.
            try {
                Laser laser = new Laser(from, to, time, 100);
                laser.start(MiSK.getInstance());
                //Laser.drawLaser(from, to, time, 100);
            } catch (ReflectiveOperationException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "draw laser from " + location1.toString(e, debug) + " to " + location2.toString(e, debug) + " for " + integer.toString(e, debug) + " seconds";
    }
}
