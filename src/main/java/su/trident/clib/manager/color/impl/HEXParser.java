package su.trident.clib.manager.color.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import su.trident.clib.manager.color.api.ColorParser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HEXParser implements ColorParser
{
    public static final int SUB_VERSION = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1]);
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([a-fA-F\\d]{6})");

    @Override
    public String parse(String message)
    {
        if (SUB_VERSION >= 16) {
            final Matcher matcher = HEX_PATTERN.matcher(message);
            final StringBuilder builder = new StringBuilder(message.length() + 32);

            while (matcher.find()) {
                String group = matcher.group(1);
                matcher.appendReplacement(builder, "§x§" + group
                        .charAt(0) + "§" + group.charAt(1) + "§" + group
                        .charAt(2) + "§" + group.charAt(3) + "§" + group.charAt(4) + "§" + group
                        .charAt(5));
            }
            message = matcher.appendTail(builder).toString();
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public List<String> parse(List<String> text)
    {
        return text.stream().map(this::parse).collect(Collectors.toList());
    }
}