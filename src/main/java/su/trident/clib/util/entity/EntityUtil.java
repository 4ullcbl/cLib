package su.trident.clib.util.entity;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class EntityUtil
{
    public static void smoothPulling(Entity toTeleport, Entity target, double speed)
    {
        final Vector direction = target.getLocation().toVector().subtract(toTeleport.getLocation().toVector()).normalize().multiply(speed);

        toTeleport.setVelocity(direction);
    }

    public static void smoothPulling(Entity toTeleport, Entity target)
    {
        smoothPulling(toTeleport, target, 3.0);
    }
}
