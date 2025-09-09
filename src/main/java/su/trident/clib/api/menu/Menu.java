package su.trident.clib.api.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Represents a custom inventory menu with clickable items.
 *
 * <p>Implementations of this interface allow developers to create interactive
 * menus for players, define items in specific slots, and handle click events.
 * By default, the {@link #open(Player)} method opens the menu for a player.</p>
 *
 * <p>This interface extends {@link InventoryHolder}, meaning that each menu
 * is backed by a Bukkit {@link org.bukkit.inventory.Inventory}.</p>
 */
public interface Menu extends InventoryHolder
{

    /**
     * Called when a player clicks on a specific slot in the menu.
     *
     * @param player the player who clicked
     * @param slot   the clicked slot index
     */
    void click(Player player, int slot);

    /**
     * Places an item in the given slot without any custom click action.
     *
     * @param item the item to place
     * @param slot the target slot index
     */
    void setItem(ItemStack item, int slot);

    /**
     * Places an item in the given slot and assigns a click action to it.
     *
     * @param item   the item to place
     * @param slot   the target slot index
     * @param click  the action to execute when a player clicks this slot
     */
    void setItem(ItemStack item, int slot, Consumer<Player> click);

    /**
     * Sets multiple items in the menu at once.
     *
     * @param items a map where keys are items and values are target slot indices
     */
    void setItems(Map<ItemStack, Integer> items);

    /**
     * Gets the item currently placed in the given slot.
     *
     * @param slot the slot index
     * @return the item at the given slot, or {@code null} if empty
     */
    ItemStack getItem(int slot);

    /**
     * Determines whether inventory click events should be cancelled by default.
     *
     * @return {@code true} if clicks are cancelled, {@code false} otherwise
     */
    boolean isCanceled();

    /**
     * Opens this menu for the given player.
     *
     * @param player the player to open the menu for
     */
    default void open(Player player)
    {
        player.openInventory(getInventory());
    }

    /**
     * Represents the number of rows a menu can have.
     * Each row contains 9 slots.
     */
    enum Rows {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6);

        private final int rows;

        Rows(int rows)
        {
            this.rows = rows;
        }

        /**
         * Gets the total number of slots represented by this row count.
         *
         * @return the number of slots (rows * 9)
         */
        public int getSlots()
        {
            return rows * 9;
        }
    }
}
