package me.camdenorrb.kportals.extensions

import me.camdenorrb.kportals.gson
import java.io.File

/**
 * Created by camdenorrb on 3/20/17.
 */

fun Any.toJson(): String = gson.toJson(this)

fun Any.writeJsonTo(file: File) = file.write { it.write(toJson()) }

fun <T : Any> String.readJson(to: Class<T>): T = gson.fromJson(this, to)