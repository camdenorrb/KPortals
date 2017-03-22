package me.camdenorrb.kportals.commands

import me.camdenorrb.kportals.KPortals
import org.bukkit.ChatColor.*
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

/**
 * Created by camdenorrb on 3/20/17.
 */

class PortalCmd(val kPortals: KPortals) : TabExecutor {

	override fun onTabComplete(sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>?) = kPortals.subCmds.map { it.arg }

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {

		if (args.isEmpty()) return { sender.sendMessage(helpMsg); true }()


		val arguments = args.toMutableList()

		val subCmdArg = arguments.removeAt(0)

		val subCmd = kPortals.subCmds.find { it.arg.equals(subCmdArg, true) }
				?: return { sender.sendMessage(helpMsg); true }()


		if (sender.hasPermission(subCmd.permission).not()) sender.sendMessage("${RED}You don't have the necessary permission node $AQUA\"${subCmd.permission}\"!")

		if (subCmd.execute(sender, kPortals, arguments).not()) sender.sendMessage("$RED${subCmd.usage}")

		return true
	}


	companion object {

		val helpMsg = arrayOf(
				"\n${GREEN}Commands:",
				"   $DARK_AQUA- /portal -create <Name> <Type> <Arguments>",
				"   $DARK_AQUA- /portal -setArgs <PortalName> <Arguments>",
				"   $DARK_AQUA- /portal -setType <PortalName> <Type>",
				"   $DARK_AQUA- /portal -remove <PortalName>",
				"   $DARK_AQUA- /portal -type <PortalName>",
				"   $DARK_AQUA- /portal -args <PortalName>",
				"   $DARK_AQUA- /portal -list",
		        "\n${GREEN}Portal Types + Args:",
				"   ${GOLD}ConsoleCommand - (Command without / , use %player% to represent the player name)",
				"   ${GOLD}PlayerCommand - (Command without /)",
				"   ${GOLD}Random - (Radius no decimals)",
				"   ${GOLD}Bungee - (Server name)",
				"   ${GOLD}World - (World name)",
				"   ${GOLD}Unknown - This is a placeholder for future use."
				)

	}

}