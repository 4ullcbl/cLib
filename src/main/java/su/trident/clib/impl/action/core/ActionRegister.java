package su.trident.clib.impl.action.core;

import lombok.Getter;
import su.trident.clib.impl.action.context.Context;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ActionRegister
{
    @Getter
    private static final Map<String, Action> actions = new HashMap<>();

    public static Action register(String key)
    {
        final Action action = new Action(key);

        actions.put(key, action);
        return action;
    }

    public static void execute(String line, Context context)
    {
        final String[] parts = line.split(" ");

        if (parts.length == 0) return;

        final String key = parts[0];
        final Action currentAction = actions.get(key);

        if (currentAction == null) return;

        currentAction.run(line, Arrays.copyOfRange(parts, 1, parts.length), context);
    }
}
