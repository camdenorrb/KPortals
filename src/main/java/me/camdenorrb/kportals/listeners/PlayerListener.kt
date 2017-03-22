package me.camdenorrb.kportals.listeners

import me.camdenorrb.kportals.KPortals
import me.camdenorrb.kportals.events.PlayerKPortalEnterEvent
import me.camdenorrb.kportals.events.PlayerMoveBlockEvent
import me.camdenorrb.kportals.portal.PortalType.*
import me.camdenorrb.kportals.position.Position
import me.camdenorrb.minibus.event.EventWatcher
import me.camdenorrb.minibus.listener.MiniListener
import org.bukkit.Bukkit
import org.bukkit.ChatColor.RED
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

/**
 * Created by camdenorrb on 3/20/17.
 */

class PlayerListener(val kPortals: KPortals) : Listener, MiniListener {

	@EventHandler(ignoreCancelled = true)
	fun onMove(event: PlayerMoveEvent) {
		val toLoc = event.to; val fromLoc = event.from
		val toBlock = toLoc.block; val fromBlock = fromLoc.block

		if (fromBlock == toBlock) return

		event.isCancelled = KPortals.miniBus.fire(PlayerMoveBlockEvent(event.player, fromBlock, toBlock, fromLoc, toLoc)).cancelled
	}

	@EventWatcher
	fun onMoveBlock(event: PlayerMoveBlockEvent) {

		val player = event.player
		val toPos = Position(event.toBlock.location)
		val portal = kPortals.portals.find { it.positions.any { it == toPos } } ?: return

		if (KPortals.miniBus.fire(PlayerKPortalEnterEvent(player, portal)).cancelled) return

		when(portal.type) {
			World -> player.teleport(Bukkit.getWorld(portal.toArgs)?.spawnLocation ?: return player.sendMessage(portalNotCorrectMsg))
			Bungee -> KPortals.sendToServer(player, portal.toArgs)
			Random -> player.teleport(toPos.randomSafePos(portal.toArgs.toIntOrNull() ?: return player.sendMessage(portalNotCorrectMsg)).toLocation())
			PlayerCommand -> player.chat("/${portal.toArgs}")
			ConsoleCommand -> Bukkit.dispatchCommand(kPortals.server.consoleSender, portal.toArgs)
			else -> return
		}

	}


	companion object {

		val portalNotCorrectMsg = "${RED}The portal you have walked into is not setup correctly."

	}

}