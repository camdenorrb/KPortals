package me.camdenorrb.kportals.messages

import org.bukkit.ChatColor.*

/**
 * Created by camdenorrb on 3/23/17.
 */

object Messages {

	val portalNotFound = "${RED}Portal not found!"

	val typeDoesNotExist = "${RED}Type does not exist!"

	val noSelection = "${RED}You have no WorldEdit selection of the portal!"

	val nameAlreadyExists = "${RED}The name you have provided already exists for another portal!"


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
			"   ${GOLD}PlayerCommand - (Command without /)",
			"   ${GOLD}Random - (Radius no decimals)",
			"   ${GOLD}Bungee - (Server name)",
			"   ${GOLD}World - (World name)",
			"   ${GOLD}Unknown - This is a placeholder for future use."
	)

}