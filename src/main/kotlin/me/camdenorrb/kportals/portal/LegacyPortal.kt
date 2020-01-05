package me.camdenorrb.kportals.portal

import me.camdenorrb.kportals.ext.readJson
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.util.Vector
import java.io.File

// TODO: Read this from json to convert to Korm
data class LegacyPortal(
    val name: String,
    var toArgs: String,
    var type: Type = Type.Unknown,
    val positions: MutableSet<Position> = mutableSetOf()
) {

    private val minimum: Vector get() {

        val min = Vector(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)

        positions.forEach {
            if (it.x < min.x) min.x = it.x
            if (it.y < min.y) min.y = it.y
            if (it.z < min.z) min.z = it.z
        }

        return min
    }

    private val maximum: Vector get() {

        val max = Vector(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)

        positions.forEach {
            if (it.x > max.x) max.x = it.x
            if (it.y > max.y) max.y = it.y
            if (it.z > max.z) max.z = it.z
        }

        return max
    }


    // Returns null if unable to modernize, TODO: Make this a result
    fun modernize(): Portal? {

        val world = positions.firstOrNull()?.worldName?.let { Bukkit.getWorld(it) } ?: return null
        val modernType = Portal.Type.byName(type.name) ?: return null
        val vectors = positions.map { Vector(it.x, it.y, it.z) }
        val selection = Portal.Selection(minimum, maximum)

        return Portal(name, toArgs, world.uid, modernType, selection, vectors.toSet())
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

    // TODO: Make a static modernize function
    data class Portals(val portals: MutableList<LegacyPortal> = mutableListOf()) {

        companion object {

            fun modernize(legacyPortalFile: File): Set<Portal> {
                return legacyPortalFile.readJson(Portals()).portals.mapNotNullTo(mutableSetOf()) { legacyPortal ->

                    val modernPortal = legacyPortal.modernize()

                    if (modernPortal == null) {
                        println("Could not modernize ${legacyPortal.name}!")
                    }

                    modernPortal
                }
            }

        }
    }

}