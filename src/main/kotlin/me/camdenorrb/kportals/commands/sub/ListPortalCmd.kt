package me.camdenorrb.kportals.commands.sub

import me.camdenorrb.kportals.KPortals
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.LIGHT_PURPLE
import org.bukkit.command.CommandSender

/**
 * Created by camdenorrb on 3/21/17.
 */

class ListPortalCmd : SubCmd("-list", "/Portal -list", "kportals.list") {

	override fun execute(sender: CommandSender, plugin: KPortals, args: MutableList<String>): Boolean {
		sender.sendMessage("${GREEN}Portals: { $LIGHT_PURPLE${plugin.portals.joinToString { it.name }} $GREEN}")
		return true
	}

}