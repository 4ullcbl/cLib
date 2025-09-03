package su.trident.clib.menu.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.function.Consumer;

public interface Menu extends InventoryHolder
{
    void click(Player player, int slot);

    void setItem(ItemStack item, int slot);

    void setItem(ItemStack item, int slot, Consumer<Player> click);

    void setItems(Map<ItemStack, Integer> items);

    ItemStack getItem(int slot);

    boolean isCanceled();

    default void open(Player player)
    {
        player.openInventory(getInventory());
    }

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

        public int getSlots()
        {
            return rows * 9;
        }
    }
}
