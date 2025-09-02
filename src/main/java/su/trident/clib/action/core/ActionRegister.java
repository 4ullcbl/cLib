package su.trident.clib.action.core;

import su.trident.clib.action.context.ActionContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ActionRegister
{
    private static final Map<String, Action> actions = new HashMap<>();

    public static Action register(String key)
    {
        final Action action = new Action(key);

        actions.put(key, action);
        return action;
    }

    public static void execute(String line, ActionContext context)
    {
        final String[] parts = line.split(" ");

        if (parts.length == 0) return;

        final String key = parts[0];
        final Action currentAction = actions.get(key);

        if (currentAction == null) return;
        currentAction.run(Arrays.copyOfRange(parts, 1, parts.length), context);
    }
}
