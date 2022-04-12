package net.approachcircle.plugin.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Mock {
	public static void mockPlayer(Player target, String[] message) {
		String joinedMessage = "";
		for (String word : message) {
			if (word.equalsIgnoreCase(target.getName())) {
				continue;
			}
			joinedMessage += word;
			joinedMessage += " ";
		}
		Bukkit.broadcastMessage("<" + target.getName() + "> " + joinedMessage);
	}
}
