package su.trident.clib.menu.impl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import su.trident.clib.menu.api.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class DefaultMenu implements Menu
{
    private final Inventory inventory;
    private final Map<Integer, Consumer<Player>> actions = new HashMap<>();
    private final boolean canceled;

    public DefaultMenu(String title, Rows rows)
    {
        this.inventory = Bukkit.createInventory(this, rows.getSlots(), title);
        this.canceled = false;
        setItems();
    }

    public DefaultMenu(String title, Rows rows, boolean canceled)
    {
        this.inventory = Bukkit.createInventory(this, rows.getSlots(), title);
        this.canceled = canceled;
        setItems();
    }

    protected abstract void setItems();

    @Override
    public void click(Player player, int slot)
    {
        final Consumer<Player> action = this.actions.get(slot);

        if (action != null)
            action.accept(player);
    }

    @Override
    public void setItem(ItemStack item, int slot)
    {
        setItem(item, slot, player -> {});
    }

    @Override
    public void setItem(ItemStack item, int slot, Consumer<Player> click)
    {
        this.actions.put(slot, click);

        getInventory().setItem(slot, item);
    }

    @Override
    public void setItems(Map<ItemStack, Integer> items)
    {
        items.keySet().forEach(item -> setItem(item, items.get(item)));
    }

    @Override
    public ItemStack getItem(int slot)
    {
        return getInventory().getItem(slot);
    }

    @Override
    public boolean isCanceled()
    {
        return canceled;
    }

    @Override
    public Inventory getInventory()
    {
        return inventory;
    }
}
