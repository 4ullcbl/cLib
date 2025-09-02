package su.trident.clib.action.context;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerActionContext extends Context
{
    private final Player player;

    public PlayerActionContext(Player player)
    {
        this.player = player;
    }

}
