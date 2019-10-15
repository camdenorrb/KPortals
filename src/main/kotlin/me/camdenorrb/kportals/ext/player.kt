package me.camdenorrb.kportals.ext

import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player
import kotlin.random.Random

fun Player.teleportToRandomLoc(radius: Int) {

    var attemptCount = 1

    while (true) {

        val randomX = (location.x - (Random.nextInt(radius * 2) - radius)).toInt()
        val randomZ = (location.z - (Random.nextInt(radius * 2) - radius)).toInt()
        val randomY = world!!.getHighestBlockYAt(randomX, randomZ)

        if (attemptCount++ >= 10) {
            player.sendMessage("${ChatColor.RED}We were unable to teleport you to a random safe location after 10 attempts!")
            break
        }

        if ((randomY - 1) <= 0 || world.getBlockAt(randomX, randomY - 1, randomZ).isLiquid) {
            attemptCount++
            continue
        }

        val toLoc = Location(world, randomX.toDouble(), randomY.toDouble(), randomZ.toDouble(), location.yaw, location.pitch)

        if (!teleport(toLoc)) {
            attemptCount++
            continue
        }

        break
    }

}
