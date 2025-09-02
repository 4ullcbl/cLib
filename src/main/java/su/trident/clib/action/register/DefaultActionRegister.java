package su.trident.clib.action.register;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import su.trident.clib.action.core.ActionRegister;

public class DefaultActionRegister
{

    public void registerAll() {
        registerSound();
        registerParticle();
        registerPotionEffect();
        registerCommand();
    }

    public void registerSound()
    {
        ActionRegister.register("[SOUND]")
                .arguments("name")
                .arguments("volume")
                .arguments("pitch")
                .playerExecutor((arguments, player) ->
                {
                    if (!isValid(player.getLocation())) return;

                    final Sound sound = Sound.valueOf(arguments.get("name", "UI_BUTTON_CLICK"));
                    final int volume = arguments.getParseInt("volume", 1);
                    final int pitch = arguments.getParseInt("pitch", 1);

                    player.getLocation().getWorld().playSound(player.getLocation(), sound, volume, pitch);

                });
    }

    public void registerParticle()
    {
        ActionRegister.register("[PARTICLE]")
                .arguments("name")
                .arguments("count")
                .arguments("speed")
                .arguments("delta_x")
                .arguments("delta_y")
                .arguments("delta_z")
                .playerExecutor((arguments, player) ->
                {
                    if (player.getLocation().getWorld() == null) return;

                    player.getLocation().getWorld().spawnParticle(
                            Particle.valueOf(arguments.get("name")),
                            player.getLocation(),
                            arguments.getParseInt("count", 3),
                            arguments.getParseDouble("speed", 0.3),
                            arguments.getParseDouble("delta_x", 0.3),
                            arguments.getParseDouble("delta_y", 0.3),
                            arguments.getParseDouble("delta_z", 0.3));
                });
    }

    public void registerPotionEffect()
    {
        ActionRegister.register("[EFFECT]")
                .arguments("name")
                .arguments("duration")
                .arguments("amplifier")
                .playerExecutor((arguments, player) ->
                {
                    final PotionEffect potionEffect = new PotionEffect(
                            PotionEffectType.getByName(arguments.get("name", "POISON")),
                            arguments.getParseInt("duration", 20),
                            arguments.getParseInt("amplifier", 0));

                    player.addPotionEffect(potionEffect);
                });
    }

    public void registerCommand()
    {

        final String key = "[COMMAND]";

        ActionRegister.register(key)
                .arguments(key)
                .playerExecutor((arguments, player) ->
                {
                    final String input = arguments.getRawInput();

                    if (input == null || input.length() <= key.length() + 1) {
                        player.sendMessage("§cВведите команду после [COMMAND]");
                        return;
                    }

                    final String command = input.substring(key.length()).trim();

                    if (command.isEmpty()) {
                        player.sendMessage("§cКоманда не может быть пустой");
                        return;
                    }

                    player.performCommand(command);
                });
    }

    private boolean isValid(Location location)
    {
        return location != null && location.getWorld() != null;
    }
}
