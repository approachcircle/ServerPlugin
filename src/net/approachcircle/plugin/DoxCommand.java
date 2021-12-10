package net.approachcircle.plugin;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DoxCommand {
	public boolean Dox(CommandSender sender, String[] args) {
		Player playerToDox = Bukkit.getPlayer(args[0]);
		Player senderAsPlayer = (Player) sender;
		Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
		for (Player player : onlinePlayers) {
			String address = playerToDox.getAddress().toString().replaceFirst("/", "");
			player.sendTitle(ChatColor.RED + address, "", 10, 40, 10);
		}
		Bukkit.broadcastMessage(ChatColor.RED + senderAsPlayer.getName() + " wanted to dox " + playerToDox.getName() + ", so here's their IP address");
		return true;
	}
	
	public boolean DoxFailed(CommandSender sender, String[] args) {
		Player senderAsPlayer = (Player) sender;
		senderAsPlayer.sendMessage(ChatColor.RED + "you're not supposed to execute this command, now you must pay the price");
		Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
		for (Player player : onlinePlayers) {
			String address = senderAsPlayer.getAddress().toString().replaceFirst("/", "");
			player.sendTitle(ChatColor.RED + address, "", 10, 40, 10);
		}
		Bukkit.broadcastMessage(ChatColor.RED + senderAsPlayer.getName() + " tried to dox someone, so here's THEIR IP address");
		return true;
	}
}
