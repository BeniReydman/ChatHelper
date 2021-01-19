package commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import main.HelperMain;

public class CommandBuilder {
    public static void buildCommands(HelperMain plugin) {
        CommandSpec help = CommandSpec.builder().description(Text.of("Help Command")).executor(new HelpCommand()).build();
        Sponge.getCommandManager().register(plugin, help, "test");
    }
}
