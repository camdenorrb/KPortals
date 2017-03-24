package me.camdenorrb.kportals.commands.sub

import com.sk89q.worldedit.bukkit.selections.CuboidSelection
import me.camdenorrb.kportals.KPortals
import me.camdenorrb.kportals.messages.Messages.*
import me.camdenorrb.kportals.portal.Portal
import me.camdenorrb.kportals.portal.PortalType
import me.camdenorrb.kportals.position.Position
import org.bukkit.Bukkit
import org.bukkit.ChatColor.*
import org.bukkit.Material.*
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Created by camdenorrb on 3/20/17.
 */

class CreatePortalCmd : SubCmd("-create", "/Portal -create <Name> <Type> <ToArg>", "kportals.create") {

	override fun execute(sender: CommandSender, kPortals: KPortals, args: MutableList<String>): Boolean {

		if (sender !is Player || args.size < 3) return false

		val name = args.removeAt(0)
		if (kPortals.portals.any { it.name.equals(name, true) }) return { NAME_ALREADY_EXISTS.send(sender); true }()

		val type = PortalType.byName(args.removeAt(0)) ?: return false
		val selection = KPortals.worldEdit.getSelection(sender)
		
		if (selection == null || selection !is CuboidSelection) return { NO_SELECTION.send(sender); true }()

		val portalSelection = getPortalIn(Position(selection.minimumPoint), Position(selection.maximumPoint))

		kPortals.portals.add(Portal(name, args.joinToString(" "), type, portalSelection))
		sender.sendMessage("${DARK_GREEN}You have successfully claimed the portal with the name: $LIGHT_PURPLE$name ${DARK_GREEN}and the type $LIGHT_PURPLE$type$DARK_GREEN!")

		return true
	}


	private fun getPortalIn(minPos: Position, maxPos: Position): MutableSet<Position> {

		val returnSet = mutableSetOf<Position>()

		val world = Bukkit.getWorld(minPos.worldName)

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