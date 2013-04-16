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

		if (active) {
			// deactivate bot
			active = false;

			if ("!stop".equalsIgnoreCase(commandLine[0]) && MODERATOR.equalsIgnoreCase(sender)) {
				sendMessage(channel, "Runde beendet!");
			} else {
				// buzzer "pressed"
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
		} else {
			// activate bot
			if ("!los".equalsIgnoreCase(commandLine[0]) && MODERATOR.equalsIgnoreCase(sender)) {
				active = true;
				sendMessage(channel, "Los gehts!");
			}
		}

		// easteregg :)
		if ("!keks".equalsIgnoreCase(commandLine[0])) {
			if (commandLine.length > 1) {
				sendAction(channel, "gibt " + commandLine[1] + " einen Keks von " + sender);
			} else {
				sendAction(channel, "gibt " + sender + " einen Keks");
			}
		}
	}
}
