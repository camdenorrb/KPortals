package me.camdenorrb.kportals.position

import me.camdenorrb.kportals.KPortals
import me.camdenorrb.kportals.iterator.PositionProgression
import me.camdenorrb.kportals.random
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

/**
 * Created by camdenorrb on 3/20/17.
 */

data class Position(val x: Double = 0.0, val y: Double = 0.0, val z: Double = 0.0, val yaw: Float = 0.0F, val pitch: Float = 0.0F, val worldName: String = "world") {

	constructor(x: Int = 0, y: Int = 0, z: Int = 0, worldName: String = "world") : this(x.toDouble(), y.toDouble(), z.toDouble(), worldName = worldName)

	constructor(loc: Location) : this(loc.x, loc.y, loc.z, loc.yaw, loc.pitch, loc.world.name)


	operator fun rangeTo(other: Position) = PositionProgression(this, other)


	fun getWorld() = Bukkit.getWorld(worldName)!!

	fun round() = Position(Math.floor(x), Math.floor(y), Math.floor(z))

	fun equalCords(pos2: Position): Boolean = x == pos2.x && z == pos2.z && y == pos2.y


	fun toLocation(world: World): Location = Location(world, x, y, z, yaw, pitch)

	fun toLocation(): Location = toLocation(KPortals.instance.server.getWorld(worldName))

	fun randomSafePos(radius: Int): Position {

		val randomX = (x - (random.nextInt(radius * 2) - radius)).toInt()
		val randomZ = (z - (random.nextInt(radius * 2) - radius)).toInt()
		val randomY = getWorld().getHighestBlockYAt(randomX, randomZ)

		return Position(randomX, randomY, randomZ, worldName)
	}

}