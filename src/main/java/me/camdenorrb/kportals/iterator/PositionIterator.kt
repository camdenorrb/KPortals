package me.camdenorrb.kportals.iterator

import me.camdenorrb.kportals.position.Position

/**
 * Created by camdenorrb on 3/20/17.
 */

// This whole thing could be a simple sequence...

/*
sequence {
    for (i in 0..x)
        for (i in 0..y)
            yield(Pos(x, y, z))
}
*/
class PositionProgression(val start: Position, val end: Position, var step: Int = 1) : Iterable<Position> {

	override fun iterator() = PositionIterator(start, end, step)

	class PositionIterator(val start: Position, val end: Position, var step: Int = 1) : Iterator<Position> {

		private var x: Double = start.x
		private var y: Double = start.y
		private var z: Double = start.z

		private val worldName: String = start.worldName


		override fun hasNext(): Boolean = x <= end.x

		override operator fun next(): Position {

			val nextPos = Position(x, y, z, worldName = worldName)

			if (++z <= end.z) return nextPos
			z = start.z

			if (++y <= end.y) return nextPos
			y = start.y

			x++

			return nextPos
		}


		infix fun step(step: Int): PositionIterator {
			this.step = step
			return this
		}

	}
}
