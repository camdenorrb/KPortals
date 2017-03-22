package me.camdenorrb.kportals.commands.sub

import me.camdenorrb.kportals.KPortals
import org.bukkit.ChatColor.*
import org.bukkit.command.CommandSender

/**
 * Created by camdenorrb on 3/22/17.
 */

class SetArgsCmd : SubCmd("-setArgs", "/Portal -setArgs (PortalName) (Args)", "kportals.setArgs") {

	override fun execute(sender: CommandSender, kPortals: KPortals, args: MutableList<String>): Boolean {

		if (args.size < 2) return false


		val name = args.removeAt(0)

		val portal = kPortals.portals.find { it.name.equals(name, true) }
				?: return { sender.sendMessage("${RED}Portal not found!"); true }()


		portal.toArgs = args.joinToString(" ")
		sender.sendMessage("${DARK_GREEN}The portal $LIGHT_PURPLE$name arguments is now { $LIGHT_PURPLE${portal.toArgs} $DARK_GREEN}")
		return true
	}

}