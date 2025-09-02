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

## Code Example

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
