package test;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import su.trident.clib.menu.impl.DefaultMenu;

public class MenuTest extends DefaultMenu
{
    public MenuTest(String title, Rows rows)
    {
        super(title, rows, true);
    }

    @Override
    protected void setItems()
    {
        setItem(getFlyItem(), 4, player -> player.setAllowFlight(!player.getAllowFlight()));
    }

    private ItemStack getFlyItem()
    {
        final ItemStack flyItem = new ItemStack(Material.PHANTOM_MEMBRANE);
        final ItemMeta meta = flyItem.getItemMeta();

        if (meta == null)
            return null;

        meta.setDisplayName("click to get fly");
        flyItem.setItemMeta(meta);

        return flyItem;
    }
}
