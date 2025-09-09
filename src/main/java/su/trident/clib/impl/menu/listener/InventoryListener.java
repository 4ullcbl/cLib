package su.trident.clib.impl.menu.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import su.trident.clib.api.menu.Menu;

public class InventoryListener implements Listener
{
    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        final Inventory inventory = event.getInventory();

        if (!(inventory.getHolder() instanceof Menu menu))
            return;

        menu.click((Player) event.getWhoClicked(), event.getSlot());

        if (menu.isCanceled())
            event.setCancelled(true);
    }
}
