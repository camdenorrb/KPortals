package me.camdenorrb.kportals.ext

import org.bukkit.util.Vector

fun Vector.sequenceTo(toVec: Vector): Sequence<Vector> {

    val min = Vector.getMinimum(this, toVec)
    val max = Vector.getMaximum(this, toVec)

    return sequence {
        for (x in min.blockX..max.blockX) {
            for (y in min.blockY..max.blockY) {
                for (z in min.blockZ..max.blockZ) {
                    yield(Vector(x, y, z))
                }
            }
        }
    }
}