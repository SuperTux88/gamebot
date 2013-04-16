package org.coding4coffee.gamebot;

import org.jibble.pircbot.PircBot;

public class GameBot extends PircBot {

	private final String moderator;
	private final String buzzerCommand;

	private boolean active = false;

	public GameBot(final String moderator, final String buzzerCommand) {
		setName("gamebot");
		this.moderator = moderator;
		this.buzzerCommand = buzzerCommand;
	}

	@Override
	protected void onMessage(final String channel, final String sender, final String login, final String hostname,
			final String message) {
		// split line
		final String[] commandLine = message.split(" ");

		if (active) {
			// deactivate bot
			active = false;

			if ("!stop".equalsIgnoreCase(commandLine[0]) && moderator.equalsIgnoreCase(sender)) {
				sendMessage(channel, "Runde beendet!");
			} else {
				// buzzer "pressed"
				sendMessage(channel, sender + " ist dran!");

				// run bash script asynchronously
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Runtime.getRuntime().exec(buzzerCommand).waitFor();
						} catch (final Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		} else {
			// activate bot
			if ("!los".equalsIgnoreCase(commandLine[0]) && moderator.equalsIgnoreCase(sender)) {
				active = true;
				sendMessage(channel, "Los gehts!");
			}
		}
	}
}
