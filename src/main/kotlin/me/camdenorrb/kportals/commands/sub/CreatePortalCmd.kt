package me.camdenorrb.kportals.commands.sub

import me.camdenorrb.kportals.KPortals
import me.camdenorrb.kportals.ext.sequenceTo
import me.camdenorrb.kportals.messages.Messages.*
import me.camdenorrb.kportals.portal.Portal
import org.bukkit.ChatColor.DARK_GREEN
import org.bukkit.ChatColor.LIGHT_PURPLE
import org.bukkit.Material.EMERALD_BLOCK
import org.bukkit.Material.REDSTONE_BLOCK
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.Vector

/**
 * Created by camdenorrb on 3/20/17.
 */

class CreatePortalCmd : SubCmd("-create", "/Portal -create <Name> <Type> <ToArg>", "kportals.create") {

	override fun execute(sender: CommandSender, plugin: KPortals, args: List<String>): Boolean {

		if (sender !is Player || args.size < 3) {
			return false
		}

		val portalName = args[0]
		val portalType = Portal.Type.byName(args[1])
		val portalArgs = args.drop(2).joinToString(" ")

		if (portalType == null) {
			TYPE_DOES_NOT_EXIST.send(sender)
			return true
		}

		if (plugin.portals.any { it.name.equals(portalName, true) }) {
			NAME_ALREADY_EXISTS.send(sender)
			return true
		}

		val selection = plugin.selectionCache[sender]

		if (selection == null || !selection.isSelected()) {
			NO_SELECTION.send(sender)
			return true
		}

		val sel1 = selection.sel1!!
		val sel2 = selection.sel2!!

		val min = Vector.getMinimum(sel1, sel2)
		val max = Vector.getMaximum(sel1, sel2)

		val world = sender.world

		val portalVectors = min.sequenceTo(max).filterTo(HashSet()) {
			world.getBlockAt(it.blockX, it.blockY, it.blockZ).type == REDSTONE_BLOCK
		}

		if (portalVectors.isEmpty()) {
			SELECTION_EMPTY.send(sender)
			return true
		}

		// Definitely could be just one iteration, but eh
		portalVectors.forEach {
			world.getBlockAt(it.blockX, it.blockY, it.blockZ).type = EMERALD_BLOCK
		}

		plugin.portals.add(Portal(portalName, portalArgs, world.uid, portalType, selection, portalVectors))

		plugin.portalsFile.parentFile?.mkdirs()
		plugin.portalsFile.createNewFile()

		plugin.korm.push(plugin.portals, plugin.portalsFile)

		sender.sendMessage("${DARK_GREEN}You have successfully claimed the portal with the name: $LIGHT_PURPLE$portalName ${DARK_GREEN}and the type $LIGHT_PURPLE$portalType$DARK_GREEN!")

		return true
	}

}