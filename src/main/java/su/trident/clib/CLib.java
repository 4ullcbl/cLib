package su.trident.clib;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import su.trident.clib.api.CLibAPI;
import su.trident.clib.api.menu.Menu;
import su.trident.clib.impl.CLibImpl;
import su.trident.clib.api.checker.UpdateChecker;
import su.trident.clib.impl.checker.GitHubChecker;
import su.trident.clib.impl.command.MainCommand;
import su.trident.clib.api.color.ColorParser;
import su.trident.clib.impl.manager.color.HEXParser;
import su.trident.clib.impl.menu.DefaultMenu;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Getter
public final class CLib extends JavaPlugin
{
    private static final String MESSAGE_PREFIX = "cLib -> ";

    @Getter
    private CLibAPI cLibAPI;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        final ColorParser hexParser = new HEXParser();
        registerCommand("clib", new MainCommand(hexParser));

        cLibAPI = new CLibImpl(this);
        Bukkit.getServicesManager().register(CLibAPI.class, cLibAPI, this, ServicePriority.Normal);

        startUpdateCheck();
        saveDefaultConfig();
    }

    @Override
    public void onDisable()
    {
    }

    private void registerCommand(String s, CommandExecutor command)
    {
        Optional.ofNullable(getCommand(s))
                .orElseThrow()
                .setExecutor(command);
    }

    private void registerListners(Listener... listeners)
    {
        Arrays.stream(listeners).forEach(l -> getServer().getPluginManager().registerEvents(l, this));
    }


    private void startUpdateCheck()
    {
        if (this.getConfig().getBoolean("update_check")) {
            final UpdateChecker updateChecker = new GitHubChecker("4ullcbl/cLib", this);
            updateChecker.start();
        }
    }

    public static String getMessagePrefix()
    {
        return MESSAGE_PREFIX;
    }


}
