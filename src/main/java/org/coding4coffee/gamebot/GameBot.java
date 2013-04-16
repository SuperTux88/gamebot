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

		final boolean isModerator = moderator.equalsIgnoreCase(sender);

		if (active) {
			if ("!stop".equalsIgnoreCase(commandLine[0]) && isModerator) {
				// deactivate bot
				active = false;
				sendMessage(channel, "Runde beendet!");
			} else if (!isModerator) {
				// buzzer "pressed"
				active = false;
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
			if ("!los".equalsIgnoreCase(commandLine[0]) && isModerator) {
				// activate bot
				active = true;
				sendMessage(channel, "Los gehts!");
			}
		}
	}
}
