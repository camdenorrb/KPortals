package me.camdenorrb.kportals.portal

import org.bukkit.Location

data class PortalSelection(var sel1: Location?, var sel2: Location?) {

    val isSelected get() = sel1 != null && sel2 != null

}