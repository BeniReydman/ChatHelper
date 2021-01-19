package commands;

import java.util.Collection;
import java.util.Collections;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Broadcast implements MessageChannel {

    public static void broadcast(String response) {
    	Text text = TextSerializers.FORMATTING_CODE.deserialize(response);
    	//Text text = Text.of(response);
        Text pikadex = Text.of(TextColors.YELLOW, "[Pikadex] ");
        text = Text.join(pikadex, text);
        Sponge.getServer().getBroadcastChannel().send(text);
    }
	
	@Override
	public Collection<MessageReceiver> getMembers() {
		return Collections.emptyList();
	}
	
}
