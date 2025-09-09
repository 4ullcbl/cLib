package su.trident.clib.impl.util.numbers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;

public class NumberUtilAsync
{
    private final JavaPlugin plugin;

    public NumberUtilAsync(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }

    public CompletableFuture<String> toRoman(int num)
    {
        final CompletableFuture<String> future = new CompletableFuture<>();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->
        {
            try {
                final String result = NumberUtil.toRoman(num);
                Bukkit.getScheduler().runTask(plugin, () -> future.complete(result));
            } catch (Exception e) {
                Bukkit.getScheduler().runTask(plugin, () -> future.completeExceptionally(e));
            }
        });

        return future;
    }
}
