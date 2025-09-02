package su.trident.clib.github;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URL;

@RequiredArgsConstructor
public class UpdateChecker
{
    private final String githubRepo;
    private final JavaPlugin plugin;

    public void checkLast()
    {
        this.plugin.getLogger().info(ChatColor.GOLD + "Проверка обновлений...");

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->
        {

            try {
                final URL url = new URL("https://api.github.com/repos/" + githubRepo + "/releases/latest");
                final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.addRequestProperty("User-Agent", "Mozilla/5.0");

                if (conn.getResponseCode() == 200) {
                    final JSONObject json = (JSONObject) new JSONParser().parse(new InputStreamReader(conn.getInputStream()));

                    final String latestVersion = (String) json.get("tag_name");
                    final String currentVersion = this.plugin.getDescription().getVersion();

                    versionInfoMessages(currentVersion, latestVersion);

                } else {
                    this.plugin.getLogger().info(ChatColor.DARK_RED + "Не удалось проверить обновление. Error: " + conn.getResponseCode());
                }

                conn.disconnect();

            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void versionInfoMessages(String currentVersion, String latestVersion)
    {
        if (!currentVersion.equalsIgnoreCase(latestVersion)) {
            this.plugin.getLogger().info(ChatColor.GREEN + "Найдено обновление: " + latestVersion + " (текущая: " + currentVersion + ")");
            this.plugin.getLogger().info(ChatColor.GREEN + "Ссылка на скачивание: https://github.com/" + githubRepo + "/releases/tag/" + latestVersion);
        } else {
            this.plugin.getLogger().info(ChatColor.AQUA + "Установленна последняя версия: " + currentVersion);
        }
    }
}
