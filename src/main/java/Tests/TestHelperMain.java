package Tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;

import commands.Broadcast;
import main.HelperMain;

public class TestHelperMain {

	private static HashMap<String, String> regexResponses = new HashMap<String, String>();

	public static void main(String args[]) {

		System.out.println("Starting Testing.");
		
		try {
			// Initialize
			String line;
			String[] lines;

			// File Reading Code
			File myObj = new File("chat_regex.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				// Regular expression line
				line = myReader.nextLine();
				lines = line.split("::");

				// Check if correct
				if (lines.length != 2) {
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

		// Get Text
		String message = "hello";

		// Get Iterator
		Iterator mapIterator = regexResponses.keySet().iterator();

		// Iterate and check if text matches any regex patterns
		while (mapIterator.hasNext()) {
			String regex = (String) mapIterator.next();
			System.out.println(regex);
			Pattern checkRegex = Pattern.compile((String) regex, Pattern.CASE_INSENSITIVE);
			Matcher regexMatcher = checkRegex.matcher(message);
			if (regexMatcher.find()) {
				System.out.println(regexResponses.get(regex));
			}
		}
		
		System.out.println("Finished Testing.");
	}
}
