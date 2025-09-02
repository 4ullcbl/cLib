package su.trident.clib.checker.impl;

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
import su.trident.clib.checker.api.UpdateChecker;

import java.net.URL;

@RequiredArgsConstructor
public class GitHubChecker implements UpdateChecker
{
    private final String githubRepo;
    private final JavaPlugin plugin;

    @Override
    public void start()
    {
        this.plugin.getLogger().info(ChatColor.GOLD + "start update check task...");

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
                    this.plugin.getLogger().info("failed check update, error code: " + conn.getResponseCode());
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
            this.plugin.getLogger().info("found new update: " + latestVersion + " (current: " + currentVersion + ")");
            this.plugin.getLogger().info("download link: https://github.com/" + githubRepo + "/releases/tag/" + latestVersion);
        } else {
            this.plugin.getLogger().info("you have last version: " + currentVersion + "!");
        }
    }
}
