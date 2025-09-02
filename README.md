# cLib 🧩 - Universal Utility Library for Spigot/Paper Plugins

A powerful utility library designed to simplify Minecraft plugin development by providing common functionality, configuration management, update checking, and customizable actions system.

## 📦 Quick Installation & Usage

### 1. Add as Dependency
[![](https://jitpack.io/v/4ullcbl/cLib.svg)](https://jitpack.io/#4ullcbl/cLib)

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

**Gradle**
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.4ullcbl:cLib:25w36a'
}
```

## Custom config example
Make it easier to create configs and retrieve objects from there

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
Add an update check on your github! so that users can download the latest versions!
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

## Config actions
You can use the built-in actions or create your own using ActionRegister
```yaml
actions:
  - "[SOUND] UI_BUTTON_CLICK 1 1"
  - "[PARTICLE] FLAME"
```

### Make custom actions (write in main class)

```java
   ActionRegister.register("[SOUND]") // The unique identifier of the action
        .arguments("name")         // Sound name
        .arguments("volume")       // Volume level (usually 0-100)
        .arguments("pitch")        // Volume pitch (usualy 0-2 where 1 is normal)
        .executor((arguments, loc) ->
        {
            Make sure that the location is in a valid world
            if (loc.getWorld() == null) return;

            // Get and convert arguments
            final Sound sound = Sound.valueOf(arguments.get("name"));
            final int volume = arguments.getParseInt("volume");
            final int pitch = arguments.getParseInt("pitch");

            // Playing the sound in the specified location
            loc.getWorld().playSound(loc, sound, volume, pitch);
        });
```

### Easy execute!
all actions specified in the config are played automatically
```java
   // Get the player who executed the command
    final Player player = (Player) sender;

    // Retrieve the list of action strings from the configuration
    for (String line : config.getStringList("actions")) {
        // Execute each action with the player's current location as context
        ActionRegister.execute(line, new ActionContext(player.getLocation()));
    }
```
