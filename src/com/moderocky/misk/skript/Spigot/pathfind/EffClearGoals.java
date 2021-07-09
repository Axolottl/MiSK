package com.moderocky.misk.skript.Spigot.pathfind;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.moderocky.misk.MiSK;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

/**
 * NOT IMPLEMENTED
 *
 * I need to fix some 1.14 crap first.
 *
 * @author TheBukor
 *
 */

public class EffClearGoals extends Effect {
    private Expression<LivingEntity> entities;

    /*
    static {
        Skript.registerEffect(EffClearGoals.class, "(clear|delete) [all] pathfind[er] goals (of|from) %livingentities%");
    }

     */

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
        entities = (Expression<LivingEntity>) expr[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "clear all pathfinder goals from " + entities.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        LivingEntity[] ents = entities.getAll(event);
        for (LivingEntity ent : ents) {
            if (!(ent instanceof Player || ent instanceof ArmorStand || ent == null)) {
                MiSK.getNMSMethods().clearPathfinderGoals(ent);
            }
        }
    }
}