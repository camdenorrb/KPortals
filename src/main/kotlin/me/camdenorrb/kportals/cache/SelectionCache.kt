package me.camdenorrb.kportals.cache

import me.camdenorrb.kcommons.base.ModuleBase
import me.camdenorrb.kportals.KPortals
import me.camdenorrb.kportals.portal.Portal
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.EquipmentSlot
import java.util.*

class SelectionCache(val plugin: KPortals) : ModuleBase, Listener {

    override val name = "Selection Cache"

    val selections = mutableMapOf<UUID, Portal.Selection>()


    var isEnabled = false
        private set


    override fun enable() {

        assert(!isEnabled) {
            "$name tried to enable while already being enabled!"
        }

        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    override fun disable() {

        assert(isEnabled) {
            "$name tried to disable while already being disabled!"
        }

        HandlerList.unregisterAll(this)
        selections.clear()
    }


    @EventHandler
    fun onHitBlock(event: PlayerInteractEvent) {

        // We don't want offhand events nor ones not for selection
        if (event.hand != EquipmentSlot.HAND || event.item != plugin.selectionItem) {
            return
        }

        val player = event.player

        val hitBlock = event.clickedBlock ?: return

        when (event.action) {

            Action.LEFT_CLICK_BLOCK -> {
                selections.getOrPut(player.uniqueId, { Portal.Selection() }).sel1 = hitBlock.location
                player.sendMessage("${ChatColor.GREEN}You have selected position one.")
            }

            Action.RIGHT_CLICK_BLOCK -> {
                selections.getOrPut(player.uniqueId, { Portal.Selection() }).sel2 = hitBlock.location
                player.sendMessage("${ChatColor.GREEN}You have selected position two.")
            }

            else -> return
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        selections.remove(event.player.uniqueId)
    }

}