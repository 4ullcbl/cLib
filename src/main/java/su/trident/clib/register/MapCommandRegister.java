package su.trident.clib.register;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.logging.Level;

public class MapCommandRegister
{

    private final Map<String, CommandExecutor> executors;
    private final JavaPlugin plugin;

    public MapCommandRegister(Map<String, CommandExecutor> executors, JavaPlugin plugin)
    {
        this.executors = executors;
        this.plugin = plugin;
    }

    public void register(String name)
    {
        final CommandExecutor executor = executors.get(name);
        final PluginCommand command = plugin.getCommand(name);

        if (!isValid(name, executor)) return;

        if (command != null) {
            command.setExecutor(executors.get(name));
            plugin.getLogger().info("command: " + name + " -> successfully received executor.");
            return;
        }
        plugin.getLogger().log(Level.SEVERE, "command: " + name + " -> unknown error.");
    }

    public void registerAll()
    {
        for (String name : executors.keySet()) {
            register(name);
        }
    }

    private boolean isValid(String name, CommandExecutor executor)
    {
        if (executor == null) {
            plugin.getLogger().info("command: " + name + " -> cannot register, executor is null.");
            return false;
        }
        return true;
    }
}
