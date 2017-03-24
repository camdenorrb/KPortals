package me.camdenorrb.kportals.messages

import org.bukkit.ChatColor.*
import org.bukkit.command.CommandSender

/**
 * Created by camdenorrb on 3/23/17.
 */

enum class Messages(val message: String) {

	PORTAL_NOT_FOUND("${RED}Portal not found!"),
	TYPE_DOES_NOT_EXIST("${RED}Type does not exist!"),
	NO_SELECTION("${RED}You have no WorldEdit selection of the portal!"),
	NAME_ALREADY_EXISTS("${RED}The name you have provided already exists for another portal!");


	override fun toString() = message

	fun send(sender: CommandSender) = sender.sendMessage(this.toString())


	companion object {

		val help = arrayOf(
				"\n${GREEN}Commands:",
				"   $DARK_AQUA- /portal -create <Name> <Type> <Arguments>",
				"   $DARK_AQUA- /portal -setArgs <PortalName> <Arguments>",
				"   $DARK_AQUA- /portal -setType <PortalName> <Type>",
				"   $DARK_AQUA- /portal -remove <PortalName>",
				"   $DARK_AQUA- /portal -type <PortalName>",
				"   $DARK_AQUA- /portal -args <PortalName>",
				"   $DARK_AQUA- /portal -list",
				"\n${GREEN}Portal Types + Args:",
				"   ${GOLD}ConsoleCommand - (Command without /  use %player% to represent the player name)",
				"   ${GOLD}PlayerCommand - (Command without /  use %player% to represent the player name))",
				"   ${GOLD}Random - (Radius no decimals)",
				"   ${GOLD}Bungee - (Server name)",
				"   ${GOLD}World - (World name)",
				"   ${GOLD}Unknown - This is a placeholder for future use."
		)

	}

}