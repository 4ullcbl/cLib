package su.trident.clib.manager.particle;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ParticleBuilder
{
    private final Particle type;

    private int count = 1;
    private double speed = 0.0;
    private double offsetX = 0.0;
    private double offsetY = 0.0;
    private double offsetZ = 0.0;
    private Particle.DustOptions options = null;
    private Object data;

    public ParticleBuilder(Particle type)
    {
        this.type = type;
    }

    public ParticleBuilder dustOptions(Particle.DustOptions options)
    {
        this.options = options;
        return this;
    }

    public ParticleBuilder data(Object data)
    {
        this.data = data;
        return this;
    }

    public ParticleBuilder offset(double offsetX, double offsetY, double offsetZ)
    {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        return this;
    }

    public ParticleBuilder offset(double offsetX, double offsetZ)
    {
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        return this;
    }

    public ParticleBuilder count(int count)
    {
        this.count = count;
        return this;
    }

    public ParticleBuilder speed(double speed)
    {
        this.speed = speed;
        return this;
    }

    public ParticleBuilder offsetX(double offsetX)
    {
        this.offsetX = offsetX;
        return this;
    }

    public ParticleBuilder offsetY(double offsetY)
    {
        this.offsetY = offsetY;
        return this;
    }

    public ParticleBuilder offsetZ(double offsetZ)
    {
        this.offsetZ = offsetZ;
        return this;
    }

    public void spawn(Location location)
    {
        if (location.getWorld() == null) return;

        if (data != null || options != null) {
            final Object object = options != null ? options : data;

            location.getWorld().spawnParticle(type, location, count, offsetX, offsetY, offsetZ, speed, object);
        } else {
            location.getWorld().spawnParticle(type, location, count, offsetX, offsetY, offsetZ, speed);
        }
    }

    public void spawn(Player player)
    {
        if (data != null || options != null) {
            final Object object = options != null ? options : data;
            player.spawnParticle(type, player.getLocation(), count, offsetX, offsetY, offsetZ, speed, object);
        } else {
            player.spawnParticle(type, player.getLocation(), count, offsetX, offsetY, offsetZ, speed);
        }
    }

    public void spawn(Player... players)
    {
        Arrays.stream(players).forEach(this::spawn);
    }

    public void spawn(Iterable<Player> players)
    {
        players.forEach(this::spawn);
    }
}
