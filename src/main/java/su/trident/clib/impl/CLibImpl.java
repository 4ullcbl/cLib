package su.trident.clib.impl;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import su.trident.clib.api.CLibAPI;
import su.trident.clib.impl.registry.ServiceRegistry;

@Getter
public class CLibImpl implements CLibAPI
{
    private final Plugin plugin;

    private ServiceRegistry registry;

    public CLibImpl(Plugin plugin)
    {
        this.plugin = plugin;
        registerService();
    }

    @Override
    public void registerService()
    {
        registry = new ServiceRegistry(plugin);
        registry.registerServices();
    }
}
