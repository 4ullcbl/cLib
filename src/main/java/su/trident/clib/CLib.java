package su.trident.clib;

import org.bukkit.plugin.java.JavaPlugin;
import su.trident.clib.action.register.DefaultActionRegister;
import su.trident.clib.checker.api.UpdateChecker;
import su.trident.clib.checker.impl.GitHubChecker;
import su.trident.clib.command.TestCommand;
import su.trident.clib.register.CommandRegister;


public final class CLib extends JavaPlugin
{
    private static final String MESSAGE_PREFIX = "cLib -> ";

    private DefaultActionRegister actionRegister;
    private CommandRegister commandRegister;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        startUpdateCheck();

        commandRegister = new CommandRegister(this);
        commandRegister.register("clib", new TestCommand(getConfig()));

        actionRegister = new DefaultActionRegister();
        actionRegister.register();


    }

    @Override
    public void onDisable()
    {
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
