package me.camdenorrb.kportals.listeners

import me.camdenorrb.kportals.KPortals
import me.camdenorrb.kportals.events.PlayerKPortalEnterEvent
import me.camdenorrb.kportals.events.PlayerMoveBlockEvent
import me.camdenorrb.kportals.ext.teleportToRandomLoc
import me.camdenorrb.kportals.portal.Portal
import me.camdenorrb.minibus.event.EventWatcher
import me.camdenorrb.minibus.listener.ListenerPriority.LAST
import me.camdenorrb.minibus.listener.MiniListener
import org.bukkit.Bukkit
import org.bukkit.ChatColor.RED
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

/**
 * Created by camdenorrb on 3/20/17.
 */
class PlayerListener(val plugin: KPortals) : Listener, MiniListener {

	@EventHandler(ignoreCancelled = true)
	fun onMove(event: PlayerMoveEvent) {

		val from = event.from
		val to = event.to ?: return

		val fromBlock = from.block
		val toBlock = to.block

		if (fromBlock == toBlock) return

		val moveBlockEvent = PlayerMoveBlockEvent(event.player, fromBlock, toBlock, from, to)
		event.isCancelled = plugin.miniBus(moveBlockEvent).isCancelled
	}

	@EventWatcher(priority = LAST)
	fun onMoveBlock(event: PlayerMoveBlockEvent) {

		if (event.isCancelled) return

		val player = event.player
		val toVec = event.toBlock.location.toVector()

		val portal = plugin.portals.find { portal ->

			if (toVec.distance(portal.center).toInt() > portal.maxRadius) {
				return@find false
			}

			portal.positions.any { it == toVec }
		} ?: return

		if (plugin.miniBus(PlayerKPortalEnterEvent(player, portal)).isCancelled) return

		println("Here0")
		when (portal.type) {
			Portal.Type.World -> player.teleport(Bukkit.getWorld(portal.toArgs)?.spawnLocation ?: return player.sendMessage(portalNotCorrectMsg))
			Portal.Type.Bungee -> plugin.sendPlayerToServer(player, portal.toArgs)
			Portal.Type.Random -> player.teleportToRandomLoc(portal.toArgs.toIntOrNull() ?: return player.sendMessage(portalNotCorrectMsg))
			Portal.Type.PlayerCommand -> player.chat("/${portal.toArgs.replace("%player%", player.name)}")
			Portal.Type.ConsoleCommand -> Bukkit.dispatchCommand(plugin.server.consoleSender, portal.toArgs.replace("%player%", player.name))
			else -> return
		}
	}


	companion object {

		val portalNotCorrectMsg = "${RED}The portal you have walked into is not setup correctly."

	}

}
