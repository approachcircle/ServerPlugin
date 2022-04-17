package net.approachcircle.plugin.misc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import net.approachcircle.plugin.dimensions.Dimension;
import net.approachcircle.plugin.dimensions.DimensionPreparer;

public class TabCompleter {
	private static List<String> completions = new ArrayList<String>();
	private static String[] splitBuffer;
	
	public static List<String> complete(String buffer) {
		splitBuffer = buffer.split(" ");
		if (StringUtil.startsWithIgnoreCase("tpa", buffer)) {
			completions.add("yes");
			completions.add("no");
			populateWithPlayers();
		} else if (StringUtil.startsWithIgnoreCase("spawnon", buffer)) {
			if (splitBuffer.length > 1) {
				for (EntityType entity : EntityType.values()) {
					completions.add(entity.toString());
				}
			} else {
				populateWithPlayers();
			}
		} else if (StringUtil.startsWithIgnoreCase("reports", buffer)) {
			completions.add("reset");
			populateWithPlayers();
		} else if (StringUtil.startsWithIgnoreCase("warpto", buffer)) {
			populateWithDimensions();
		} else if (StringUtil.startsWithIgnoreCase("send", buffer)) {
			if (splitBuffer.length > 1) {
				populateWithDimensions();
			} else {
				populateWithPlayers();
			}
		} else if (StringUtil.startsWithIgnoreCase("mock", buffer)) {
			if (splitBuffer.length < 1) {
				populateWithPlayers();
			}
		}
		completions.sort(java.util.Comparator.naturalOrder());
		return completions;
	}
	
	private static void populateWithPlayers() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			completions.add(player.getName());
		}
	}
	
	private static void populateWithDimensions() {
		for (Dimension dimensions : DimensionPreparer.getAllDimensions()) {
			completions.add(dimensions.getName());
		}
	}
}
