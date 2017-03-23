package me.camdenorrb.kportals.commands.sub

import me.camdenorrb.kportals.KPortals
import me.camdenorrb.kportals.messages.Messages
import org.bukkit.ChatColor.DARK_GREEN
import org.bukkit.ChatColor.LIGHT_PURPLE
import org.bukkit.command.CommandSender

/**
 * Created by camdenorrb on 3/20/17.
 */

class RemovePortalCmd : SubCmd("-remove", "/Portal -remove <Name>", "kportals.remove") {

	override fun execute(sender: CommandSender, kPortals: KPortals, args: MutableList<String>): Boolean {

		if (args.isEmpty()) return false

		if (kPortals.portals.removeIf { it.name.equals(args[0], true) }.not()) return { sender.sendMessage(Messages.portalNotFound); true }()


		sender.sendMessage("${DARK_GREEN}Successful removed $LIGHT_PURPLE${args[0]} ${DARK_GREEN}from existing portals.")
		return true
	}

}