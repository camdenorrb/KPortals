package me.camdenorrb.kportals.commands.sub

import me.camdenorrb.kportals.KPortals
import me.camdenorrb.kportals.messages.Messages
import org.bukkit.ChatColor.*
import org.bukkit.command.CommandSender

/**
 * Created by camdenorrb on 3/22/17.
 */

class TypeCmd : SubCmd("-type", "/Portal -type <PortalName>", "kportals.type") {

	override fun execute(sender: CommandSender, kPortals: KPortals, args: MutableList<String>): Boolean {

		if (args.isEmpty()) return false


		val portal = kPortals.portals.find { it.name.equals(args[0], true) }
				?: return { sender.sendMessage(Messages.portalNotFound); true }()


		sender.sendMessage("${DARK_GREEN}The type of the portal: $AQUA${portal.name}$DARK_GREEN, is $LIGHT_PURPLE${portal.type}!")
		return true
	}
}