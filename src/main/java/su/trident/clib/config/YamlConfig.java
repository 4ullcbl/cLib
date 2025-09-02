package su.trident.clib.config;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import su.trident.clib.action.context.ActionContext;
import su.trident.clib.action.core.ActionRegister;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;

public class YamlConfig
{
    private final JavaPlugin plugin;
    @Getter
    private final File file;
    @Getter
    private final YamlConfiguration config;
    @Getter
    private String name;

    public YamlConfig(File file, JavaPlugin plugin)
    {
        this.plugin = plugin;
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        saveConfig(file.getName());
    }

    public YamlConfig(String name, JavaPlugin plugin)
    {
        this.plugin = plugin;
        this.name = name;
        this.file = new File(plugin.getDataFolder(), toYamlFormat(name));
        this.config = YamlConfiguration.loadConfiguration(file);
        saveConfig();
    }

    public YamlConfig(String name, String addedPath, JavaPlugin plugin)
    {
        this.plugin = plugin;
        this.name = name;

        final File directory = createDirectory(addedPath, plugin);

        this.file = new File(directory, toYamlFormat(name));
        this.config = YamlConfiguration.loadConfiguration(file);
        saveDirectoryConfig();
    }

    private File createDirectory(String addedPath, JavaPlugin plugin)
    {
        final File directory = new File(plugin.getDataFolder(), addedPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    public void save() throws IOException
    {
        config.save(file);
    }

    public void save(File file) throws IOException
    {
        config.save(file);
    }

    public void trySave()
    {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "config save error: " + e);
        }
    }

    private void saveDirectoryConfig()
    {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();

                final InputStream resourceStream = plugin.getResource(toYamlFormat(name));

                if (resourceStream != null) {
                    Files.copy(resourceStream, file.toPath());
                } else {
                    file.createNewFile();
                    trySave();
                }
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Ошибка создания конфига: " + e);
            }
        }
    }

    public void trySave(File file)
    {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "config save error: " + e);
        }
    }

    public void load() throws IOException, InvalidConfigurationException
    {
        config.load(file);
    }

    public void tryLoad()
    {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(File file) throws IOException, InvalidConfigurationException
    {
        config.load(file);
    }

    public void tryLoad(File file)
    {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().info("error load config: " + e);
        }
    }

    public void saveConfig()
    {
        if (!file.exists()) {
            plugin.saveResource(toYamlFormat(name), false);
        }
    }

    public void saveConfig(String name)
    {
        if (!file.exists()) {
            plugin.saveResource(toYamlFormat(name), false);
        }
    }

    public Material getMaterial(String path)
    {
        return getEnum(path, Material.class, "material");
    }

    public Material getMaterial(String path, Material defaultValue)
    {
        final Material material = getEnum(path, Material.class, "material");

        return material == null ? defaultValue : material;
    }

    public Sound getSound(String path)
    {
        return getEnum(path, Sound.class, "sound");
    }

    public String getMessage(String value)
    {
        if (value == null) return null;

        String message = config.getString("messages." + value);

        if (message == null) return null;

        return message;
    }

    public String getMessageSave(String value)
    {
        if (value == null) return " ";

        String message = config.getString("messages." + value);

        if (message == null) return " ";

        return message;
    }

    public String getMessage(String value, String def)
    {
        final String message = getMessage(value);

        return message == null ? def : message;
    }


    public Sound getSound(String path, Sound defaultValue)
    {
        final Sound sound = getEnum(path, Sound.class, "sound");

        return sound == null ? defaultValue : sound;
    }

    public Particle getParticle(String path)
    {
        return getEnum(path, Particle.class, "particle");
    }

    public Particle getParticle(String path, Particle defaultValue)
    {
        final Particle particle = getEnum(path, Particle.class, "particle");

        return particle == null ? defaultValue : particle;
    }

    public PotionEffectType getPotionEffectType(String path)
    {
        if (isNotValid(path)) return null;

        try {
            return PotionEffectType.getByName(config.getString(path));
        } catch (Exception e) {
            logError(path, "potion_effect_type");
            return null;
        }
    }

    public PotionEffectType getPotionEffectType(String path, PotionEffectType defaultValue)
    {
        if (getPotionEffectType(path) == null) return defaultValue;

        return getPotionEffectType(path);
    }

    private boolean isNotValid(String path)
    {
        return file == null || config == null || path == null;
    }

    private String toYamlFormat(String name)
    {
        return name.endsWith(".yml") ? name : name + ".yml";
    }

    private void getAction(Player player, String path)
    {
        final List<String> list = config.getStringList(path);

        for (String l: list) {
            ActionRegister.execute(l, new ActionContext(player.getLocation()));
        }
    }

    private void getAction(Location location, String path)
    {
        final List<String> list = config.getStringList(path);

        for (String l: list) {
            ActionRegister.execute(l, new ActionContext(location));
        }
    }

    private void logError(String path, String typeName)
    {
        plugin.getLogger().log(Level.SEVERE, "YamlConfig error: [" + path + "] -> " + typeName + " not found!");
    }

    public <T extends Enum<T>> T getEnum(String path, Class<T> enumClass, String typeName)
    {
        if (isNotValid(path)) return null;

        try {
            final String value = config.getString(path);
            if (value == null) return null;

            return Enum.valueOf(enumClass, value.toUpperCase());

        } catch (Exception e) {
            logError(path, typeName);
            return null;
        }
    }

    public <T extends Enum<T>> T getEnum(String path, Class<T> enumClass)
    {
        if (isNotValid(path)) return null;

        try {
            final String value = config.getString(path);
            if (value == null) return null;

            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (Exception e) {
            logError(path, enumClass.toString());
            return null;
        }
    }
}