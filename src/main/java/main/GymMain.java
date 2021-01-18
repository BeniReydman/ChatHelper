package main;

import com.google.inject.Inject;
import commands.CommandBuilder;

import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import java.sql.*;
import db_handler.DBHandler;
import db_entities.*;


@Plugin(id = "gymplugin", name = "GymPlugin", version = "1.0", description = "Gym Leader Plugin for Pixelmon")
public class GymMain {

    @Inject
    private Logger logger;

    public static void main(String[] args) {
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("GymPlugin is running.");
    }

    @Listener
    public void onInitialization(GameInitializationEvent event) {
    	// Connect to databases
    	if(!DBHandler.connect())
    	{
    		// If connection fails, database does not exist
    		DBHandler.createNewDatabase();
    		DBHandler.createTables();
    	}
    	
        CommandBuilder.buildCommands(this);
    }

}
