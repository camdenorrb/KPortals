package me.camdenorrb.kportals.commands.sub

import me.camdenorrb.kportals.KPortals
import org.bukkit.ChatColor
import org.bukkit.ChatColor.DARK_GREEN
import org.bukkit.ChatColor.LIGHT_PURPLE
import org.bukkit.command.CommandSender

/**
 * Created by camdenorrb on 3/22/17.
 */

class ArgsCmd : SubCmd("-args", "/Portal -args <PortalName>", "kportals.args") {

	override fun execute(sender: CommandSender, kPortals: KPortals, args: MutableList<String>): Boolean {

		if (args.isEmpty()) return false

		val portal = kPortals.portals.find { it.name.equals(args[0], true) } ?: return false

		sender.sendMessage("${DARK_GREEN}The arguments of the portal: ${ChatColor.AQUA}${portal.name}$DARK_GREEN, is { $LIGHT_PURPLE${portal.toArgs} $DARK_GREEN}!")
		return true
	}

}