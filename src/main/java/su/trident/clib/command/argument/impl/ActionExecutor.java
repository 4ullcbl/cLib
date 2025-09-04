package su.trident.clib.command.argument.impl;

import org.bukkit.entity.Player;
import su.trident.clib.CLib;
import su.trident.clib.action.context.PlayerActionContext;
import su.trident.clib.action.core.ActionRegister;
import su.trident.clib.command.argument.api.ArgumentExecutor;

import java.util.Arrays;

public class ActionExecutor extends ArgumentExecutor
{
    @Override
    public String getName()
    {
        return "action";
    }

    @Override
    public String getUsage()
    {
        return "/clib {action}";
    }

    @Override
    public void execute(Player player, String[] args)
    {

        final String line = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        if (!line.startsWith("[")) {
            sendHelpMessage(player);
            return;
        }

        ActionRegister.execute(line, new PlayerActionContext(player));

        player.sendMessage(CLib.getMessagePrefix() + "execute action: " + line);

    }
}
