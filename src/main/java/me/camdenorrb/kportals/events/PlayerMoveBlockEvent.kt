package me.camdenorrb.kportals.events

import me.camdenorrb.minibus.event.CancellableMiniEvent
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * Created by camdenorrb on 3/20/17.
 */

data class PlayerMoveBlockEvent(val player: Player, val fromBlock: Block, val toBlock: Block, val fromLoc: Location, val toLoc: Location) : CancellableMiniEvent()