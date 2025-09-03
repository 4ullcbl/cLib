package su.trident.clib.command.argument.impl;

import org.bukkit.entity.Player;
import su.trident.clib.command.argument.api.ArgumentExecutor;
import su.trident.clib.menu.api.Menu;
import su.trident.clib.menu.impl.DefaultMenu;
import test.MenuTest;

public class MenuCommand extends ArgumentExecutor
{
    @Override
    public String getName()
    {
        return "menu";
    }

    @Override
    public String getUsage()
    {
        return "/clib menu";
    }

    @Override
    public void execute(Player player, String[] args)
    {
        if (args.length == 0) {
            sendHelpMessage(player);
            return;
        }

        final DefaultMenu menu = new MenuTest("fly menu", Menu.Rows.ONE);
        menu.open(player);

        sendMessage(player, "fly menu open for you!");
    }
}
