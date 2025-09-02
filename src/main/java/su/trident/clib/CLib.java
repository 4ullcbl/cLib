package su.trident.clib;

import org.bukkit.plugin.java.JavaPlugin;
import su.trident.clib.github.UpdateChecker;

public final class CLib extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

    }

    @Override
    public void onDisable()
    {
    }


    private void startUpdateCheck()
    {
        if (this.getConfig().getBoolean("update_check")) {
            final UpdateChecker uc = new UpdateChecker("4ullcbl/cLib", this);
            uc.checkLast();
        }
    }
}
