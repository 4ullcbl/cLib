package su.trident.clib.impl.action.core;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import su.trident.clib.impl.action.context.ActionContext;
import su.trident.clib.impl.action.context.Context;
import su.trident.clib.impl.action.context.PlayerActionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class Action
{
    private final String key;
    private final List<String> arguments = new ArrayList<>();
    private BiConsumer<ActionArguments, Location> executor;
    private BiConsumer<ActionArguments, Player> playerExecutor;

    public Action arguments(String key)
    {
        arguments.add(key);
        return this;
    }

    @Deprecated
    public void executor(BiConsumer<ActionArguments, Location> executor)
    {
        this.executor = executor;
    }

    public void playerExecutor(BiConsumer<ActionArguments, Player> playerExecutor)
    {
        this.playerExecutor = playerExecutor;
    }

    public void run(String rawInput, String[] values, Context context)
    {
        final ActionArguments actionArgs = new ActionArguments(rawInput);

        for (int i = 0; i < Math.min(values.length, arguments.size()); i++) {
            actionArgs.put(arguments.get(i), values[i]);
        }

        if (context instanceof ActionContext) {
            executor.accept(actionArgs, ((ActionContext) context).getLocation());
        } else if (context instanceof PlayerActionContext) {
            playerExecutor.accept(actionArgs, ((PlayerActionContext) context).getPlayer());
        }
    }
}
