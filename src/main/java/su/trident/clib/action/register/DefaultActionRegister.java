package su.trident.clib.action.register;

import org.bukkit.Particle;
import org.bukkit.Sound;
import su.trident.clib.action.core.ActionRegister;

public class DefaultActionRegister
{
    public void register()
    {
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

        ActionRegister.register("[PARTICLE]")
                .arguments("name")
                .executor((arguments, loc) ->
                {
                    if (loc.getWorld() == null) return;

                    loc.getWorld().spawnParticle(Particle.valueOf(arguments.get("name")), loc, 3, 0.05, 0.05 ,0.05, 0.05);
                });
    }
}
