package me.camdenorrb.kportals.portal

import org.bukkit.Location
import org.bukkit.util.Vector
import java.util.*
import kotlin.math.max

/**
 * Created by camdenorrb on 3/20/17.
 */

data class Portal(
    val name: String,
    var toArgs: String,
    val worldUUID: UUID,
    var type: Type = Type.Unknown,
    val positions: Set<Vector> = setOf()
) {

    @delegate:Transient
    private val minimum by lazy {

        val minBlockX = positions.map(Vector::getBlockX).min()!!
        val minBlockY = positions.map(Vector::getBlockY).min()!!
        val minBlockZ = positions.map(Vector::getBlockZ).min()!!

        Vector(minBlockX, minBlockY, minBlockZ)
    }

    @delegate:Transient
    private val maximum by lazy {

        val minBlockX = positions.map(Vector::getBlockX).max()!!
        val minBlockY = positions.map(Vector::getBlockY).max()!!
        val minBlockZ = positions.map(Vector::getBlockZ).max()!!

        Vector(minBlockX, minBlockY, minBlockZ)
    }

    @delegate:Transient
    val center by lazy {

        val centerX = (maximum.blockX - minimum.blockX) + minimum.blockX
        val centerY = (maximum.blockY - minimum.blockY) + minimum.blockY
        val centerZ = (maximum.blockZ - minimum.blockZ) + minimum.blockZ

        Vector(centerX, centerY, centerZ)
    }

    @delegate:Transient
    val maxRadius by lazy {

        val centerX = maximum.blockX - minimum.blockX
        val centerY = maximum.blockY - minimum.blockY
        val centerZ = maximum.blockZ - minimum.blockZ

        max(max(centerX, centerY), centerZ)
    }


    enum class Type {

        ConsoleCommand, PlayerCommand, Unknown, Random, Bungee, World;

        companion object {

            fun byName(name: String, ignoreCase: Boolean = true): Type? {
                return values().find { name.equals(it.name, ignoreCase) }
            }

        }
    }

    data class Selection(var sel1: Location? = null, var sel2: Location? = null) {

        val isSelected get() = sel1 != null && sel2 != null

    }

}