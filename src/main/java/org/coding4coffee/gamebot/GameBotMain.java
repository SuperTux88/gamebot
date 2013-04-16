package org.coding4coffee.gamebot;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class GameBotMain {

	private static GameBot bot;

	public static void main(final String[] args) throws NickAlreadyInUseException, IOException, IrcException {
		if (args.length < 3) {
			System.out.println("usage: java -jar gamebot.runnable.jar channel moderator buzzer-command");
			System.exit(1);
		}

		bot = new GameBot(args[1], args[2]);
		bot.setVerbose(true);
		bot.setAutoNickChange(true);
		bot.connect("irc.theradio.cc");
		bot.joinChannel(args[0]);
	}
}
