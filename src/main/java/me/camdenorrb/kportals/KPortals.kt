package me.camdenorrb.kportals

import com.google.common.io.ByteStreams
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sk89q.worldedit.WorldEdit
import me.camdenorrb.kportals.commands.PortalCmd
import me.camdenorrb.kportals.commands.sub.*
import me.camdenorrb.kportals.ext.readJson
import me.camdenorrb.kportals.listeners.PlayerListener
import me.camdenorrb.kportals.portal.Portal
import me.camdenorrb.kportals.portal.Portals
import me.camdenorrb.minibus.MiniBus
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Created by camdenorrb on 3/20/17.
 */

// TODO: Link portals

class KPortals : JavaPlugin() {

	val miniBus: MiniBus = MiniBus()

	val subCmds = mutableSetOf(CreatePortalCmd(), RemovePortalCmd(), ListPortalCmd(), SetArgsCmd(), SetTypeCmd(), TypeCmd(), ArgsCmd())


	lateinit var portals: MutableList<Portal>
		private set

	lateinit var portalFile: File
		private set



	override fun onLoad() {
		instance = this
		portalFile = File(dataFolder, "portals.json")
		portals = portalFile.readJson(Portals()).portals
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

	}

	override fun onDisable() {
		subCmds.clear()
		miniBus.cleanUp()
		Portals(portals).writeJsonTo(portalFile).also { portals.clear() }
	}


	companion object {

		lateinit var instance: KPortals
			private set


		/*
		fun sendToServer(player: Player, toServer: String) {

			val out = ByteStreams.newDataOutput().apply {
				writeUTF("Connect")
				writeUTF(toServer)
			}

			player.sendPluginMessage(instance, "BungeeCord", out.toByteArray())
		}
		*/

	}
}