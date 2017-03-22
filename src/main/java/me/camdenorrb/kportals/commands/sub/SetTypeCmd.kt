package me.camdenorrb.kportals.commands.sub

import me.camdenorrb.kportals.KPortals
import me.camdenorrb.kportals.portal.PortalType
import org.bukkit.ChatColor.DARK_GREEN
import org.bukkit.ChatColor.RED
import org.bukkit.command.CommandSender

/**
 * Created by camdenorrb on 3/22/17.
 */

class SetTypeCmd : SubCmd("-setType", "/Portal -setType <Name> <Type>", "kportals.setType") {

	override fun execute(sender: CommandSender, kPortals: KPortals, args: MutableList<String>): Boolean {

		if (args.size != 2) return false


		val name = args[0]

		val type = PortalType.byName(args.removeAt(0))
				?: return { sender.sendMessage("${RED}Type does not exist!"); true }()

		val portal = kPortals.portals.find { it.name.equals(name, true) }
				?: return { sender.sendMessage("${RED}Portal not found!"); true }()


		portal.type = type
		sender.sendMessage("${DARK_GREEN}Successfully set ${portal.name} to $type")

		return true

	}

}