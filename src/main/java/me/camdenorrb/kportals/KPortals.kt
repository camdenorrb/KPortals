package me.camdenorrb.kportals

import com.google.common.io.ByteStreams
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sk89q.worldedit.bukkit.WorldEditPlugin
import me.camdenorrb.kportals.commands.PortalCmd
import me.camdenorrb.kportals.commands.sub.*
import me.camdenorrb.kportals.extensions.readJson
import me.camdenorrb.kportals.extensions.writeJsonTo
import me.camdenorrb.kportals.listeners.PlayerListener
import me.camdenorrb.kportals.portal.Portal
import me.camdenorrb.kportals.portal.Portals
import me.camdenorrb.minibus.MiniBus
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

/**
 * Created by camdenorrb on 3/20/17.
 */

// TODO: Link portals

val random = Random()

val gson: Gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

class KPortals : JavaPlugin() {

	lateinit var portals: MutableList<Portal>

	val subCmds = mutableSetOf(CreatePortalCmd(), RemovePortalCmd(), ListPortalCmd(), SetArgsCmd(), SetTypeCmd(), TypeCmd(), ArgsCmd())


	override fun onLoad() {
		instance = this
		portalFile = File(dataFolder, "portals.json")
		portals = portalFile.readJson(Portals()).portals
		worldEdit = WorldEditPlugin.getPlugin(WorldEditPlugin::class.java)
	}

	override fun onEnable() {

		// Register the main command.
		getCommand("portal").executor = PortalCmd(this)

		// Register PlayerListener
		val playerListener = PlayerListener(this)
		miniBus.register(playerListener)
		server.pluginManager.registerEvents(playerListener, this)

		// Enable BungeeCord plugin channel.
		server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")

	}

	override fun onDisable() {
		subCmds.clear()
		miniBus.cleanUp()
		Portals(portals).writeJsonTo(portalFile).also { portals.clear() }
	}


	companion object {

		lateinit var instance: KPortals
			private set

		lateinit var worldEdit: WorldEditPlugin
			private set

		lateinit var portalFile: File
			private set

		val miniBus: MiniBus = MiniBus()


		fun sendToServer(player: Player, toServer: String) {
			val out = ByteStreams.newDataOutput()
			out.writeUTF("Connect")
			out.writeUTF(toServer)

			player.sendPluginMessage(instance, "BungeeCord", out.toByteArray())
		}

	}
}