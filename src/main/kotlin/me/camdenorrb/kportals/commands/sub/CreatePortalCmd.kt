package me.camdenorrb.kportals.commands.sub

import com.boydti.fawe.FaweAPI
import me.camdenorrb.kportals.KPortals
import me.camdenorrb.kportals.messages.Messages.NAME_ALREADY_EXISTS
import me.camdenorrb.kportals.messages.Messages.NO_SELECTION
import me.camdenorrb.kportals.portal.Portal
import me.camdenorrb.kportals.position.Position
import org.bukkit.Bukkit
import org.bukkit.ChatColor.DARK_GREEN
import org.bukkit.ChatColor.LIGHT_PURPLE
import org.bukkit.Material.EMERALD_BLOCK
import org.bukkit.Material.REDSTONE_BLOCK
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Created by camdenorrb on 3/20/17.
 */

class CreatePortalCmd : SubCmd("-create", "/Portal -create <Name> <Type> <ToArg>", "kportals.create") {

	override fun execute(sender: CommandSender, plugin: KPortals, args: List<String>): Boolean {

		if (sender !is Player || args.size < 3) {
			return false
		}

		val portalName = args[0]
		val portalType = args[1]

		if (plugin.portals.any { it.name.equals(portalName, true) }) {
			NAME_ALREADY_EXISTS.send(sender)
			return true
		}

		val type = Portal.Type.byName()plugin.portals.find { it.name ==  }.byName(args.removeAt(0)) ?: return false
		val selection = FaweAPI.wrapPlayer(sender).selection
		
		if (selection == null) {
			NO_SELECTION.send(sender)
			return true
		}

		val portalSelection = getPortalIn(Position(selection.minimumPoint, sender.world.name), Position(selection.maximumPoint, sender.world.name))

		plugin.portals.add(Portal(name, args.joinToString(" "), type, portalSelection))
		plugin.korm.push(plugin.portals, plugin.portalsFile)

		sender.sendMessage("${DARK_GREEN}You have successfully claimed the portal with the name: $LIGHT_PURPLE$name ${DARK_GREEN}and the type $LIGHT_PURPLE$type$DARK_GREEN!")

		return true
	}


	private fun getPortalIn(minPos: Position, maxPos: Position): MutableSet<Position> {

		val returnSet = mutableSetOf<Position>()

		val world = Bukkit.getWorld(minPos.worldName)!!

		for (x in minPos..maxPos) {

			val block = x.toLocation(world).block

			if (block.type == REDSTONE_BLOCK) {
				block.type = EMERALD_BLOCK
				returnSet.add(x)
			}

		}

		return returnSet
	}

}