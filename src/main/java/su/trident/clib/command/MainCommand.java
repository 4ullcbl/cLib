package su.trident.clib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import su.trident.clib.CLib;
import su.trident.clib.action.core.ActionRegister;
import su.trident.clib.command.argument.api.ArgumentExecutor;
import su.trident.clib.command.argument.impl.ActionExecutor;
import su.trident.clib.command.argument.impl.ColorParserArgument;
import su.trident.clib.manager.color.api.ColorParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainCommand implements TabExecutor
{
    private final FileConfiguration config;
    private final ColorParser parser;
    @SuppressWarnings("all")
    private final Map<String, ArgumentExecutor> argumentExecutors = new HashMap<>();

    public MainCommand(FileConfiguration config, ColorParser parser)
    {
        this.config = config;
        this.parser = parser;
        argumentExecutors.put("action", new ActionExecutor());
        argumentExecutors.put("parse", new ColorParserArgument(parser));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CLib.getMessagePrefix() + "only player can use!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(CLib.getMessagePrefix() + "please put arguments!");
            return true;
        }

        final String message = args[0];
        final Player player = (Player) sender;

        try {
            argumentExecutors.get(message).execute(player, args);
            return true;
        } catch (Exception e) {
            if (message == null) {
                sender.sendMessage(CLib.getMessagePrefix() + "please put arguments!");
                return true;
            }

            sender.sendMessage(CLib.getMessagePrefix() + "no executor was found for argument {" + message + "}");
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args)
    {
        final List<String> tab = new ArrayList<>();

        if (args.length == 1 && !argumentExecutors.keySet().isEmpty()) {
            tab.addAll(argumentExecutors.keySet());
        }

        if (args.length == 2 && !ActionRegister.getActions().keySet().isEmpty()) {
            tab.addAll(ActionRegister.getActions().keySet());
        }

        return tab;
    }
}
