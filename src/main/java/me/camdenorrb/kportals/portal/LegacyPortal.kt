package me.camdenorrb.kportals.portal

import org.bukkit.Location
import java.util.*

// TODO: Read this from json to convert to Korm
data class LegacyPortal(
    val name: String,
    var toArgs: String,
    val worldUUID: UUID,
    var type: Type = Type.Unknown,
    val positions: MutableSet<Position> = mutableSetOf()
) {

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

}