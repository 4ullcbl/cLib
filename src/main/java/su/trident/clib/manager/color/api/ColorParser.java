package su.trident.clib.manager.color.api;

import java.util.List;

public interface ColorParser
{
    String parse(String message);

    List<String > parse(List<String> message);
}
