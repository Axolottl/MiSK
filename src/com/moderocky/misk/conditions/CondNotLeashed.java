package me.sharpjaws.sharpsk.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class CondNotLeashed extends Condition {
    private Expression<Entity> en;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, SkriptParser.ParseResult arg3) {
        en = (Expression<Entity>) expr[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "%entity% is not leashed";
    }

    @Override
    public boolean check(Event e) {
        boolean check = false;
        try {
            LivingEntity en2 = (LivingEntity) en.getSingle(e);
            check = !en2.isLeashed();
        } catch (NullPointerException ignored) {
        }
        return check;
    }
}
