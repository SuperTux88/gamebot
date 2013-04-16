package org.coding4coffee.gamebot;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class GameBotMain {

	private static GameBot bot;

	public static void main(final String[] args) throws NickAlreadyInUseException, IOException, IrcException {
		bot = new GameBot();
		bot.setVerbose(true);
		bot.setAutoNickChange(true);
		bot.connect("irc.theradio.cc");
		bot.joinChannel("#theradiocc");
	}
}
