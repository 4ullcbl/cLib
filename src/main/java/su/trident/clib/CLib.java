package su.trident.clib;

import lombok.Getter;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import su.trident.clib.action.register.DefaultActionRegister;
import su.trident.clib.checker.api.UpdateChecker;
import su.trident.clib.checker.impl.GitHubChecker;
import su.trident.clib.command.MainCommand;
import su.trident.clib.manager.color.api.ColorParser;
import su.trident.clib.manager.color.impl.HEXParser;
import su.trident.clib.manager.particle.ParticleBuilder;
import su.trident.clib.menu.listener.InventoryListener;
import su.trident.clib.register.CommandRegister;

@Getter
public final class CLib extends JavaPlugin
{
    private static final String MESSAGE_PREFIX = "cLib -> ";

    private CommandRegister commandRegister;
    private DefaultActionRegister actionRegister;
    private ColorParser hexParser;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        startUpdateCheck();


        actionRegister = new DefaultActionRegister();
        actionRegister.registerAll();

        hexParser = new HEXParser();

        commandRegister = new CommandRegister(this);
        commandRegister.register("clib", new MainCommand(getConfig(), getHexParser()));

        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

    @Override
    public void onDisable()
    {
        commandRegister = null;
        actionRegister = null;
        hexParser = null;
    }


    private void startUpdateCheck()
    {
        if (this.getConfig().getBoolean("update_check")) {
            final UpdateChecker updateChecker = new GitHubChecker("4ullcbl/cLib", this);
            updateChecker.start();
        }
    }

    public static String getMessagePrefix() {
        return MESSAGE_PREFIX;
    }

}
