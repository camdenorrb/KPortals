package me.camdenorrb.kportals.commands.sub

import me.camdenorrb.kportals.KPortals
import me.camdenorrb.kportals.messages.Messages.PORTAL_NOT_FOUND
import me.camdenorrb.kportals.messages.Messages.TYPE_DOES_NOT_EXIST
import me.camdenorrb.kportals.portal.Portal
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.DARK_GREEN
import org.bukkit.command.CommandSender

/**
 * Created by camdenorrb on 3/22/17.
 */

class SetTypeCmd : SubCmd("-setType", "/Portal -setType <Name> <Type>", "kportals.setType") {

	override fun execute(sender: CommandSender, plugin: KPortals, args: MutableList<String>): Boolean {

		if (args.size != 2) return false


		val name = args[0]

		val type = Portal.Type.byName(args[1])
				?: return { TYPE_DOES_NOT_EXIST.send(sender); true }()

		val portal = plugin.portals.find { it.name.equals(name, true) }
				?: return { PORTAL_NOT_FOUND.send(sender); true }()


		portal.type = type

		sender.sendMessage("${DARK_GREEN}Successfully set $AQUA${portal.name}'s ${DARK_GREEN}type to $AQUA$type")

		return true

	}

}