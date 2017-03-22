package me.camdenorrb.kportals.commands.sub

import me.camdenorrb.kportals.KPortals
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

		sender.sendMessage("$LIGHT_PURPLE${portal.name}'s ${DARK_GREEN}arguments are { $LIGHT_PURPLE${portal.toArgs} $DARK_GREEN}")
		return true
	}

}