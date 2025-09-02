package su.trident.clib.register;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRegister
{
    private final JavaPlugin plugin;

    public CommandRegister(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }

    public void register(String name, CommandExecutor executor)
    {
        final PluginCommand command = plugin.getCommand(name);

        if (executor == null || command == null) {
            return;
        }

        command.setExecutor(executor);
    }
}
