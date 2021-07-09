package com.moderocky.misk.skript.Spigot.entity;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.moderocky.misk.utils.EntityUtils;
import com.moderocky.misk.utils.MerchantUtils;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.inventory.Merchant;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Not implemented yet.
 */

@Name("Get Rider/Driver")
@Description({
        "Gets the rider, or driver, of an entity."})
@Examples("set {_d} to the driver of {_boaty}")
@Since("0.2.5")
public class ExprRider extends SimpleExpression<Entity> {
    private Expression<Entity> entityExpression;

    /*
    static {
        Skript.registerExpression(ExprRider.class, Entity.class, ExpressionType.SIMPLE,
                "[the] (driver|rider) of %entity%");
    }

     */

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.entityExpression = (Expression<Entity>) exprs[0];
        return true;
    }

    @Override
    protected Entity[] get(Event e) {
        List<Entity> entities   = new ArrayList<>();
        Entity vehicle          = entityExpression.getSingle(e);
        Entity driver           = EntityUtils.getRider(vehicle);
        if (driver != null)
            entities.add(driver);
        return entities.toArray(new Entity[0]);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Entity> getReturnType() {
        return Entity.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the driver of " + entityExpression.toString(e, debug);
    }

}
