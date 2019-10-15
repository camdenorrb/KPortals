package me.camdenorrb.kportals

import com.google.common.io.ByteStreams
import com.sxtanna.korm.Korm
import me.camdenorrb.kportals.cache.SelectionCache
import me.camdenorrb.kportals.commands.PortalCmd
import me.camdenorrb.kportals.commands.sub.*
import me.camdenorrb.kportals.ext.readJson
import me.camdenorrb.kportals.listeners.PlayerListener
import me.camdenorrb.kportals.portal.LegacyPortal
import me.camdenorrb.kportals.portal.Portal
import me.camdenorrb.minibus.MiniBus
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Created by camdenorrb on 3/20/17.
 */

// TODO: Link portals

class KPortals : JavaPlugin() {

	val korm = Korm()

	val miniBus: MiniBus = MiniBus()

	val selectionCache = SelectionCache(this)

	val subCmds = mutableSetOf(CreatePortalCmd(), RemovePortalCmd(), ListPortalCmd(), SetArgsCmd(), SetTypeCmd(), SelectCmd(), TypeCmd(), ArgsCmd())

	lateinit var portals: MutableList<Portal>
		private set

	val portalsFile by lazy {
		File(dataFolder, "portals.korm")
	}

	val selectionItem = ItemStack(Material.WOODEN_PICKAXE).apply {
		itemMeta = itemMeta?.apply {

			setDisplayName("${ChatColor.AQUA}Portal Selection Item")

			lore = listOf("KPortals")
			isUnbreakable = true // To allow us to use .equals since it checks durability
		}
	}


	override fun onLoad() {

		instance = this

		val legacyPortalFile = File(dataFolder, "portals.json")

		if (!portalsFile.exists() && legacyPortalFile.exists()) {
			portals = legacyPortalFile.readJson(LegacyPortal.Portals()).portals
			korm.push(portals, portalsFile)
		}
		else if (portalsFile.exists()) {
			portals = korm.pull(portalsFile).to()!!
		}
	}

	override fun onEnable() {

		// Register the main command.
		getCommand("portal")!!.setExecutor(PortalCmd(this))

		// Register PlayerListener
		val playerListener = PlayerListener(this)

		miniBus.register(playerListener)
		server.pluginManager.registerEvents(playerListener, this)

		// Enable BungeeCord plugin channel.
		server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")

		selectionCache.enable()
	}

	override fun onDisable() {

		// Clean up
		portals.clear()
		subCmds.clear()
		miniBus.cleanUp()

		// Disable modules
		selectionCache.disable()
	}


	fun sendPlayerToServer(player: Player, toServer: String) {

		val out = ByteStreams.newDataOutput().apply {
			writeUTF("Connect")
			writeUTF(toServer)
		}

		player.sendPluginMessage(instance, "BungeeCord", out.toByteArray())
	}


	companion object {

		lateinit var instance: KPortals
			private set

	}
}