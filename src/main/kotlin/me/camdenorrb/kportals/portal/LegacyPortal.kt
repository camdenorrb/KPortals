package me.camdenorrb.kportals.portal

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.util.Vector

// TODO: Read this from json to convert to Korm
data class LegacyPortal(
    val name: String,
    var toArgs: String,
    var type: Type = Type.Unknown,
    val positions: MutableSet<Position> = mutableSetOf()
) {

    // Returns null if unable to modernize, TODO: Make this a result
    fun modernize(): Portal? {

        val worldUUID = positions.firstOrNull()?.worldName?.let { Bukkit.getWorld(it) }?.uid ?: return null
        val modernType = Portal.Type.byName(type.name) ?: return null
        val vectors = positions.map { Vector(it.x, it.y, it.z) }

        return Portal(name, toArgs, worldUUID, modernType, vectors.toSet())
    }

    // Retyped here to ensure legacy support
    enum class Type {

        ConsoleCommand, PlayerCommand, Unknown, Random, Bungee, World;

        companion object {

            fun byName(name: String): Type? {
                return values().find { name.equals(it.name, true) }
            }

        }
    }

    data class Position(val x: Double = 0.0, val y: Double = 0.0, val z: Double = 0.0, val yaw: Float = 0.0F, val pitch: Float = 0.0F, val worldName: String = "world") {

        constructor(x: Int = 0, y: Int = 0, z: Int = 0, worldName: String = "world") : this(x.toDouble(), y.toDouble(), z.toDouble(), worldName = worldName)

        constructor(loc: Location) : this(loc.x, loc.y, loc.z, loc.yaw, loc.pitch, loc.world!!.name)

    }

    data class Portals(val portals: MutableList<LegacyPortal> = mutableListOf())

}