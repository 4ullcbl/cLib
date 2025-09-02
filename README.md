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

## 🔎How get the last version?
jitpack -> https://jitpack.io/#4ullcbl/cLib/25w36a


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

## Config actions
You can use the built-in actions or create your own using ActionRegister
```yaml
actions:
  - "[SOUND] UI_BUTTON_CLICK 1 1"
  - "[PARTICLE] FLAME"
```

### Make custom actions (write in main class)

```java
    ActionRegister.register("[SOUND]") // action name
                .arguments("name") // arguments
                .arguments("volume")
                .arguments("pitch")
                .executor((arguments, loc) ->
                {
                    //execute logic
                    if (loc.getWorld() == null) return;

                    final Sound sound = Sound.valueOf(arguments.get("name"));
                    final int volume = arguments.getParseInt("volume");
                    final int pitch = arguments.getParseInt("pitch");

                    loc.getWorld().playSound(loc, sound, volume, pitch);

                });
```

### Easy execute!
all actions specified in the config are played automatically
```java
    //get player
    final Player player = (Player) sender;
        
        for (String line: config.getStringList("actions")) {
            // execute your actions! 
            ActionRegister.execute(line, new ActionContext(player.getLocation()));
        }
```
