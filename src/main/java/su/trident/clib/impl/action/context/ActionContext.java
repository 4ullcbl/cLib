package su.trident.clib.impl.action.context;

import lombok.Getter;
import org.bukkit.Location;

@Getter
@Deprecated
public class ActionContext extends Context
{
    private final Location location;

    public ActionContext(Location location)
    {
        this.location = location;
    }
}
