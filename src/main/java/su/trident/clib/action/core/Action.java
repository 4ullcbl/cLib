package su.trident.clib.action.core;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import su.trident.clib.action.context.ActionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class Action
{
    private final String key;
    private final List<String> arguments = new ArrayList<>();
    private BiConsumer<ActionArguments, Location> executor;

    public Action arguments(String key)
    {
        arguments.add(key);
        return this;
    }

    public void executor(BiConsumer<ActionArguments, Location> executor)
    {
        this.executor = executor;
    }

    public void run(String[] values, ActionContext context)
    {
        final ActionArguments actionArgs = new ActionArguments();

        for (int i = 0; i < Math.min(values.length, arguments.size()); i++) {
            actionArgs.put(arguments.get(i), values[i]);
        }

        if (executor != null) {
            executor.accept(actionArgs, context.getLocation());
        }
    }
}
