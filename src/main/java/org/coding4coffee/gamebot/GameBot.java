package org.coding4coffee.gamebot;

import org.jibble.pircbot.PircBot;

public class GameBot extends PircBot {

	private static final String MODERATOR = "SuperTux88";

	private boolean active = false;

	public GameBot() {
		setName("gamebot");
	}

	@Override
	protected void onMessage(final String channel, final String sender, final String login, final String hostname,
			final String message) {
		// split line
		final String[] commandLine = message.split(" ");

		if (!active) {
			// activate bot
			if ("!los".equalsIgnoreCase(commandLine[0]) && MODERATOR.equalsIgnoreCase(sender)) {
				active = true;
				sendMessage(channel, "Los gehts!");
			}
		} else {
			// buzzer "pressed"
			active = false;
			sendMessage(channel, sender + " ist dran!");

			// run bash script asynchronously
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Runtime.getRuntime().exec("/home/benjamin/bin/java-exec-test.sh").waitFor();
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}

		// easteregg :)
		if ("!keks".equalsIgnoreCase(commandLine[0])) {
			sendAction(channel, "gibt " + sender + " einen Keks");
		}
	}
}
