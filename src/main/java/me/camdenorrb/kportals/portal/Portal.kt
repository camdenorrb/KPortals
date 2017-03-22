package me.camdenorrb.kportals.portal

import me.camdenorrb.kportals.portal.PortalType.Unknown
import me.camdenorrb.kportals.position.Position

/**
 * Created by camdenorrb on 3/20/17.
 */

data class Portal(val name: String, var toArgs: String, var type: PortalType = Unknown, val positions: MutableSet<Position> = mutableSetOf())