# cLib 🧩

A universal utility library for Spigot/Paper plugins that simplifies plugin development and provides common functionality.

## 📦 Installation

### 1. Add as Dependency

**Maven:**
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.4ullcbl</groupId>
    <artifactId>cLib</artifactId>
    <version>25w36a</version>
</dependency>
```
### 2. Start use!

## Custom config example

```java
public final class CLib extends JavaPlugin {
    private YamlConfig tests;
    private YamlConfig messages;

    @Override
    public void onEnable() {
        // Save default configuration
        saveDefaultConfig();

        // Create custom config in nested directory structure
        tests = new YamlConfig("tests", "folder1/folder2", this);
        tests.tryLoad();

        // Create custom config in plugin data folder
        messages = new YamlConfig("messages", this);
        messages.tryLoad();

        // Retrieve configuration values with fallback defaults
        getLogger().info("Particle from custom config: " + tests.getParticle("particle"));
        getLogger().info("Message from config: " + messages.getMessage("hi_message", "default message"));
    }
}
```

## Update check example

```java
public final class CLib extends JavaPlugin {

    @Override
    public void onEnable() {
        // Create update checker instance for GitHub repository
        final UpdateChecker updateChecker = new GitHubChecker("4ullcbl/cLib", this);
        
        // Start asynchronous update check against latest GitHub tag
        updateChecker.start();
    }
}
```

## Config actions:
```yaml
actions:
  - "[SOUND] UI_BUTTON_CLICK 1 1"
  - "[PARTICLE] FLAME"
```

### Make custom actions (write in main class):

```java
    ActionRegister.register("[SOUND]")
                .arguments("name")
                .arguments("volume")
                .arguments("pitch")
                .executor((arguments, loc) ->
                {
                    if (loc.getWorld() == null) return;

                    final Sound sound = Sound.valueOf(arguments.get("name"));
                    final int volume = arguments.getParseInt("volume");
                    final int pitch = arguments.getParseInt("pitch");

                    loc.getWorld().playSound(loc, sound, volume, pitch);

                });
```

### Easy execute!
```java
    final Player player = (Player) sender;
        
        for (String line: config.getStringList("actions")) {
            ActionRegister.execute(line, new ActionContext(player.getLocation()));

            System.out.println("успешно выполнено: " + line);
        }
```
