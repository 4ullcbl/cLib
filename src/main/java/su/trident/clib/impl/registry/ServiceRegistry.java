package su.trident.clib.impl.registry;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import su.trident.clib.impl.action.register.DefaultActionRegister;
import su.trident.clib.api.color.ColorParser;
import su.trident.clib.impl.manager.color.HEXParser;
import su.trident.clib.impl.menu.listener.InventoryListener;

public class ServiceRegistry
{
    private final Plugin plugin;

    public ServiceRegistry(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Getter
    private DefaultActionRegister actionRegister;
    @Getter
    private ColorParser hexParser;

    public void registerServices()
    {
        actionRegister = new DefaultActionRegister();
        actionRegister.registerAll();

        hexParser = new HEXParser();

        plugin.getServer().getPluginManager().registerEvents(new InventoryListener(), plugin);
    }
}
