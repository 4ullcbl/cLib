package su.trident.clib.impl.command.argument;

import org.bukkit.entity.Player;
import su.trident.clib.api.argument.ArgumentExecutor;
import su.trident.clib.api.color.ColorParser;

import java.util.Arrays;

public class ColorParserArgument extends ArgumentExecutor
{
    private final ColorParser parser;

    public ColorParserArgument(ColorParser parser)
    {
        this.parser = parser;
    }

    @Override
    public String getName()
    {
        return "parse";
    }

    @Override
    public String getUsage()
    {
        return "/clib parse {text}";
    }

    @Override
    public void execute(Player player, String[] args)
    {
        try {
            final String toParse = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            sendMessage(player, "result: " + parser.parse(toParse));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
