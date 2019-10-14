package me.camdenorrb.kportals.position

import com.sk89q.worldedit.math.BlockVector3

/**
 * Created by camdenorrb on 3/20/17.
 */

// TODO: Just use vectors like a normal huemin, maybe with type alias
/*
data class Position(val x: Double = 0.0, val y: Double = 0.0, val z: Double = 0.0, val yaw: Float = 0.0F, val pitch: Float = 0.0F, val worldName: String = "world") {

	constructor(x: Int = 0, y: Int = 0, z: Int = 0, worldName: String = "world") : this(x.toDouble(), y.toDouble(), z.toDouble(), worldName = worldName)

	constructor(loc: Location) : this(loc.x, loc.y, loc.z, loc.yaw, loc.pitch, loc.world!!.name)

	constructor(vec: BlockVector3, worldName: String = "world") : this(vec.x, vec.y, vec.z, worldName = worldName)


    operator fun rangeTo(other: Position) = PositionProgression(this, other)


	fun getWorld() = Bukkit.getWorld(worldName)!!

	fun round() = Position(floor(x), floor(y), floor(z))

	fun equalCords(pos2: Position): Boolean = x == pos2.x && z == pos2.z && y == pos2.y


	fun toLocation(world: World): Location = Location(world, x, y, z, yaw, pitch)

	fun toLocation(): Location = KPortals.instance.server.getWorld(worldName)?.let { toLocation(it) } ?: error("Couldn't convert pos to loc")

	fun randomSafePos(radius: Int): Position {

		val randomX = (x - (Random.nextInt(radius * 2) - radius)).toInt()
		val randomZ = (z - (Random.nextInt(radius * 2) - radius)).toInt()
		val randomY = getWorld().getHighestBlockYAt(randomX, randomZ)

		return Position(randomX, randomY, randomZ, worldName)
	}

}*/