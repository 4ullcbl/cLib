package su.trident.clib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import su.trident.clib.CLib;
import su.trident.clib.action.context.ActionContext;
import su.trident.clib.action.context.PlayerActionContext;
import su.trident.clib.action.core.ActionRegister;

public class TestCommand implements CommandExecutor
{
    private final FileConfiguration config;

    public TestCommand(FileConfiguration config)
    {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CLib.getMessagePrefix() + "only player can use!");
            return true;
        }

        final Player player = (Player) sender;

        for (String line: config.getStringList("actions")) {
            ActionRegister.execute(line, new PlayerActionContext(player));

            player.sendMessage("Успешно выполнено: " + line);
        }

        player.sendMessage(CLib.getMessagePrefix() + "successfully execute!");

        return true;
    }
}
