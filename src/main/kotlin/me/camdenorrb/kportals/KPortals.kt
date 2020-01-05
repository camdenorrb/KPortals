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
import org.bukkit.inventory.ItemFlag
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


	lateinit var portals: MutableSet<Portal>
		private set


	val portalsFile by lazy {
		File(dataFolder, "portals.korm")
	}

	val selectionItem = ItemStack(Material.DIAMOND_AXE).apply {
		itemMeta = itemMeta?.apply {
			setDisplayName("${ChatColor.AQUA}Portal Selection Item")
			lore = listOf("KPortals")
			addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
			isUnbreakable = true // To allow us to use .equals since it checks durability
		}
	}


	override fun onLoad() {
		instance = this
	}

	override fun onEnable() {

/*
		korm.pushWith<Location> { writer, data ->

			data ?: return@pushWith

			writer.writeName("worldUUID")
			writer.writeData(data.world.uid)

			writer.writeName("vector")
			writer.writeData(data.toVector())

			if (data.yaw != 0.0F) {
				writer.writeName("yaw")
				writer.writeData(data.yaw)
			}

			if (data.pitch != 0.0F) {
				writer.writeName("pitch")
				writer.writeData(data.pitch)
			}
		}

		korm.pullWith<Location> { reader, types ->

			val worldUUID = types.find { it.key.data == "worldUUID" }?.let { reader.mapData<UUID>(it.asBase()?.data) } ?: return@pullWith null
			val vector = types.find { it.key.data == "vector" }?.let { reader.mapData<Vector>(it.asBase()?.data) } ?: return@pullWith null
			val yaw = types.find { it.key.data == "yaw" }?.let { reader.mapData<Float>(it.asBase()?.data) } ?: 0.0F
			val pitch = types.find { it.key.data == "pitch" }?.let { reader.mapData<Float>(it.asBase()?.data) } ?: 0.0F

			val world = server.getWorld(worldUUID) ?: return@pullWith null

			vector.toLocation(world, yaw, pitch)
		}
*/

		// Load legacy data, needs to be done onEnable
		val legacyPortalFile = File(dataFolder, "portals.json")

		if (!portalsFile.exists() && legacyPortalFile.exists()) {

			println("Loading legacy data")

			portals = legacyPortalFile.readJson(LegacyPortal.Portals()).portals.mapNotNull { legacyPortal ->

				val modernPortal = legacyPortal.modernize()

				if (modernPortal == null) {
					println("Could not modernize ${legacyPortal.name}!")
				}

				modernPortal
			}.toMutableSet()

			korm.push(portals, portalsFile)
			println("Done loading legacy data")
		}
		else if (portalsFile.exists()) {
			portals = korm.pull(portalsFile).toList<Portal>().mapTo(mutableSetOf()) {
				it.copy() // Copy so delegates don't get fucked up
			}
		}
		else {
			portals = mutableSetOf()
		}


		// Register the main command.
		getCommand("portal")!!.setExecutor(PortalCmd(this))

		// Register PlayerListener
		val playerListener = PlayerListener(this)

		miniBus.register(playerListener)
		server.pluginManager.registerEvents(playerListener, this)

		// Enable BungeeCord plugin channel.
		server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")

		// Enable modules
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