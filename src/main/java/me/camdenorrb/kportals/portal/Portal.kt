package me.camdenorrb.kportals.portal

import me.camdenorrb.kportals.portal.PortalType.Unknown
import me.camdenorrb.kportals.position.Position
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
    val positions: MutableSet<Vector> = mutableSetOf()
) {


    enum class Type {

        ConsoleCommand, PlayerCommand, Unknown, Random, Bungee, World;

        companion object {

            fun byName(name: String): Type? {
                return values().find { name.equals(it.name, true) }
            }

        }
    }
}