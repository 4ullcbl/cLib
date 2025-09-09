package su.trident.clib.impl.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import su.trident.clib.api.menu.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Default abstract implementation of {@link Menu}.
 *
 * <p>This implementation provides a ready-to-use inventory with support
 * for assigning click actions to slots. Actions are stored in a map where
 * each slot can have a {@link Consumer} executed when clicked.</p>
 *
 * <p>By default, all clicks can be cancelled depending on the value of
 * {@link #isCanceled()}.</p>
 *
 * <p>To use this class, extend it and implement {@link #setItems()},
 * which defines the initial layout of the menu.</p>
 */
public abstract class DefaultMenu implements Menu
{
    private final Inventory inventory;
    private final Map<Integer, Consumer<Player>> actions = new HashMap<>();
    private final boolean canceled;

    /**
     * Creates a new menu with the given title and number of rows.
     * Clicks will not be cancelled by default.
     *
     * @param title the title of the menu
     * @param rows  the number of rows in the menu
     */
    public DefaultMenu(String title, Rows rows)
    {
        this.inventory = Bukkit.createInventory(this, rows.getSlots(), title);
        this.canceled = false;
        setItems();
    }

    /**
     * Creates a new menu with the given title, rows and cancel setting.
     *
     * @param title    the title of the menu
     * @param rows     the number of rows in the menu
     * @param canceled whether clicks should be cancelled
     */
    public DefaultMenu(String title, Rows rows, boolean canceled)
    {
        this.inventory = Bukkit.createInventory(this, rows.getSlots(), title);
        this.canceled = canceled;
        setItems();
    }

    /**
     * Called when the menu is initialized to populate its items.
     * Subclasses must implement this to define the menu layout.
     */
    protected abstract void setItems();

    /**
     * Executes the click action associated with the given slot, if any.
     */
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
        setItem(item, slot, player ->
        {
        });
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
