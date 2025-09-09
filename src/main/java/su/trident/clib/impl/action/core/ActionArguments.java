package su.trident.clib.impl.action.core;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class ActionArguments
{
    private final Map<String, String> argument = new HashMap<>();
    @Getter
    @Setter
    private String rawInput;

    public ActionArguments(String rawInput) {
        this.rawInput = rawInput;
    }

    public void put(String s1, String s2)
    {
        argument.put(s1, s2);
    }

    public String get(String s1)
    {
        return argument.get(s1);
    }

    public String get(String s1, String def)
    {
        final String value = argument.get(s1);

        return value == null ? def : value;
    }

    public int getParseInt(String s1)
    {
        final String argument = get(s1);

        if (argument == null) {
            return 0;
        }

        return Integer.parseInt(argument);
    }

    public double getParseDouble(String s1)
    {
        final String argument = get(s1);

        if (argument == null) {
            return 0;
        }

        return Double.parseDouble(argument);
    }


    public int getParseInt(String s1, int def)
    {
        if (s1 == null) return def;

        try {
            return Integer.parseInt(argument.get(s1));
        } catch (NumberFormatException | NullPointerException e) {
            return def;
        }
    }

    public double getParseDouble(String s1, double def)
    {
        if (s1 == null) return def;

        try {
            return Double.parseDouble(argument.get(s1));
        } catch (NumberFormatException | NullPointerException e) {
            return def;
        }
    }

}
