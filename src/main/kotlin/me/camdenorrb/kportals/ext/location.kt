package me.camdenorrb.kportals.ext

import org.bukkit.Location
import kotlin.random.Random

fun Location.randomSafeLoc(radius: Int): Location {

    val randomX = x - (Random.nextInt(radius * 2) - radius)
    val randomZ = z - (Random.nextInt(radius * 2) - radius)
    val randomY = world!!.getHighestBlockYAt(randomX.toInt(), randomZ.toInt()).toDouble()

    return Location(world, randomX, randomY, randomZ, yaw, pitch)
}