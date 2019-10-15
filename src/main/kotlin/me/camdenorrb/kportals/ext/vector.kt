package me.camdenorrb.kportals.ext

import org.bukkit.util.Vector

// Expects you to provide min --> max
fun Vector.sequenceTo(toVec: Vector): Sequence<Vector> {

    val thisVec = this

    return sequence {
        for (x in thisVec.blockX..toVec.blockX) {
            for (y in thisVec.blockY..toVec.blockY) {
                for (z in thisVec.blockZ..toVec.blockZ) {
                    yield(Vector(x, y, z))
                }
            }
        }
    }
}