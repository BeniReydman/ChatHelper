package main;

import com.google.inject.Inject;
import commands.CommandBuilder;

import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "chathelper", name = "ChatHelper", version = "1.0", description = "Chat Helper for Minecraft")
public class HelperMain {

    @Inject
    private Logger logger;

    public static void main(String[] args) {
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("ChatHelper is running.");
    }

    @Listener
    public void onInitialization(GameInitializationEvent event) {
    	// Add Commands
        CommandBuilder.buildCommands(this);
    }

}
