package me.camdenorrb.kportals.portal

import org.bukkit.Location
import org.bukkit.util.Vector
import java.util.*

/**
 * Created by camdenorrb on 3/20/17.
 */

// TODO: Take in the world here
data class Portal(
    val name: String,
    var toArgs: String,
    val worldUUID: UUID,
    var type: Type = Type.Unknown,
    val positions: Set<Vector> = setOf()
) {


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