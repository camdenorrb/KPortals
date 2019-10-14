package me.camdenorrb.kportals.events

import me.camdenorrb.kportals.portal.Portal
import me.camdenorrb.kportals.portal.PortalType
import org.bukkit.entity.Player

/**
 * Created by camdenorrb on 3/20/17.
 */

data class PlayerKPortalEnterEvent(val player: Player, val portal: Portal, val portalType: PortalType = portal.type) {

    var isCancelled: Boolean = false

}