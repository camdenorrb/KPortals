package me.camdenorrb.kportals.commands.sub

import me.camdenorrb.kportals.KPortals
import org.bukkit.ChatColor.DARK_GREEN
import org.bukkit.ChatColor.LIGHT_PURPLE
import org.bukkit.command.CommandSender

/**
 * Created by camdenorrb on 3/20/17.
 */

class RemovePortalCmd : SubCmd("-remove", "/Portal -remove <Name>", "kportals.remove") {

	override fun execute(sender: CommandSender, kPortals: KPortals, args: MutableList<String>): Boolean {

		if (args.isEmpty() || kPortals.portals.removeIf { it.name.equals(args[0], true) }.not()) return false

		sender.sendMessage("${DARK_GREEN}Successful removed $LIGHT_PURPLE${args[0]} ${DARK_GREEN}from existing portals.")

		return true
	}

}