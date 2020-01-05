package me.camdenorrb.kportals.portal

import org.bukkit.util.Vector
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.max

/**
 * Created by camdenorrb on 3/20/17.
 */

// TODO: Embed selection as a parameter so you don't need to calculate min/max ever again
data class Portal(
    val name: String,
    var toArgs: String,
    val worldUUID: UUID,
    var type: Type = Type.Unknown,
    val selection: Selection,
    val positions: Set<Vector>
) {

    @delegate:Transient
    val maxRadius by lazy {

        val sel1 = selection.sel1 ?: return@lazy 0.0
        val sel2 = selection.sel2 ?: return@lazy 0.0

        val dist = sel1.clone().subtract(sel2)

        max(max(dist.x.absoluteValue, dist.y.absoluteValue), dist.z.absoluteValue)
    }


    enum class Type {

        ConsoleCommand, PlayerCommand, Unknown, Random, Bungee, World;

        companion object {

            fun byName(name: String, ignoreCase: Boolean = true): Type? {
                return values().find { name.equals(it.name, ignoreCase) }
            }

        }
    }

    data class Selection(var sel1: Vector? = null, var sel2: Vector? = null) {

        fun isSelected(): Boolean {
            return sel1 != null && sel2 != null
        }

        fun center(): Vector? {

            val sel1 = sel1 ?: return null
            val sel2 = sel2 ?: return null

            return sel1.clone().add(sel2).multiply(0.5)
        }

    }

}