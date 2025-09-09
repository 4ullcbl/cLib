package su.trident.clib.api.argument;

import org.bukkit.entity.Player;
import su.trident.clib.CLib;

public abstract class ArgumentExecutor
{
    public abstract String getName();
    public abstract String getUsage();

    public abstract void execute(Player player, String[] args);

    public void sendPermissionMessage(Player player)
    {
        player.sendMessage(CLib.getMessagePrefix() + "you need permission, to execute this command");
    }

    public void sendHelpMessage(Player player)
    {
        player.sendMessage(CLib.getMessagePrefix() + "usage: " + this.getUsage() + " | please, try again.");
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(CLib.getMessagePrefix() + message);
    }
}
