package Tests;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializer;
import org.spongepowered.api.text.serializer.TextSerializers;

public class TestText {
	public static void main(String args[]) {
		Text text = Text.of("&cTest");
		Text text2 = TextSerializers.FORMATTING_CODE.deserialize("&cTest");
		System.out.println("");
	}
}
