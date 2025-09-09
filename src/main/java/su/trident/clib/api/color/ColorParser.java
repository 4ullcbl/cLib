package su.trident.clib.api.color;

import java.util.List;

public interface ColorParser
{
    String parse(String message);

    List<String > parse(List<String> message);
}
