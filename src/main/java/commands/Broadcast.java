package commands;

import java.util.Collection;
import java.util.Collections;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;

public class Broadcast implements MessageChannel {

    public static void broadcast(String response) {
    	Text text = Text.of(response);
        text = Text.of(TextColors.YELLOW, "[Pikadex]", TextColors.RESET, text);
        Sponge.getServer().getBroadcastChannel().send(text);
    }
	
	@Override
	public Collection<MessageReceiver> getMembers() {
		return Collections.emptyList();
	}
	
}
