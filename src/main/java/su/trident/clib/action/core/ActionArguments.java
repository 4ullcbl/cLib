package su.trident.clib.action.core;

import java.util.HashMap;
import java.util.Map;

public class ActionArguments
{
    private final Map<String, String> argument = new HashMap<>();

    public void put(String s1, String s2) {
        argument.put(s1, s2);
    }

    public String get(String s1) {
        return argument.get(s1);
    }

    public int getParseInt(String s1) {
        return Integer.parseInt(argument.get(s1));
    }
}
