package main;

import com.google.inject.Inject;
import commands.CommandBuilder;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.io.File;  
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.util.HashMap;

import commands.*;

@Plugin(id = "chathelper", name = "ChatHelper", version = "1.0", description = "Chat Helper for Minecraft")
public class HelperMain {
	
	private HashMap<String, String> regexResponses = new HashMap<String, String>();

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
        
        // Read in .txt file
        try {
        	// Initialize
        	String regex;
        	String response;
        	
        	// File Reading Code
        	File myObj = new File("database/chat_regex.txt");
        	Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
            	// Regular expression line
            	regex = myReader.nextLine();
            	
            	// Check if response exists
            	if(!myReader.hasNextLine()) {
            		System.out.println("Incorrect file format.");
            		regexResponses.clear();
            		break;
            	}
            	
            	// Response expression line
            	response = myReader.nextLine();
            	
            	// Place into map
            	regexResponses.put(regex, response);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
        	System.out.println("An error occurred.");
        	e.printStackTrace();
        }
    }

    @Listener
    public void onChat(MessageChannelEvent.Chat event) {
    	// Put onto scheduler
    	Scheduler scheduler = Sponge.getScheduler();
    	Task.Builder taskBuilder = scheduler.createTaskBuilder();
    	taskBuilder.execute(new Runnable() {
            public void run() {
            	// Get Text
                Text message = event.getMessage().toText();
            	Broadcast.broadcast("You wrote: " + message.toPlain());
            	System.out.println("Executed broadcast.");
            }
        }).submit(this);
    }

}
