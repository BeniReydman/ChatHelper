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
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        readFile();
    }

    @Listener
    public void onChat(MessageChannelEvent.Chat event) {
    	// Put onto scheduler
    	Scheduler scheduler = Sponge.getScheduler();
    	Task.Builder taskBuilder = scheduler.createTaskBuilder();
    	taskBuilder.execute(new Runnable() {
            public void run() {
            	// Get Text
                String message = event.getRawMessage().toPlain();
                
                String result = checkMessage(message);
                if(result != "") {
                	Broadcast.broadcast(result);
                }
            }
        }).submit(this);
    }

    public String checkMessage(String message)
    {
    	// Initialize
    	String toReturn = "";
    	
    	// Get Iterator
        Iterator mapIterator = regexResponses.keySet().iterator();
        
        // Iterate and check if text matches any regex patterns
        while(mapIterator.hasNext())
        {
        	String regex = (String) mapIterator.next();
    		Pattern checkRegex = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    		Matcher regexMatcher = checkRegex.matcher(message);
    		if (regexMatcher.find()) 
    		{
    			toReturn = regexResponses.get(regex);
    		}
        }
        
        return toReturn;
    }
    
    public void readFile()
    {
    	try {
        	// Initialize
        	String line;
        	String [] lines;
        	
        	// File Reading Code
        	File myObj = new File("database/chat_regex.txt");
        	Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
            	// Regular expression line
            	line = myReader.nextLine();
            	lines = line.split("::");
            	
            	// Check if correct
            	if(lines.length != 2)
            	{
            		System.out.println("[ChatHelper] Incorrect formatting.");
            		break;
            	}
            	
            	// Place into map
            	regexResponses.put(lines[0], lines[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
        	System.out.println("An error occurred.");
        	e.printStackTrace();
        }
    }
}
