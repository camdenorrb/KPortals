package me.camdenorrb.kportals.extensions

import com.google.common.io.Files
import me.camdenorrb.kportals.gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import kotlin.reflect.KClass

/**
 * Created by camdenorrb on 3/20/17.
 */

inline fun <R> File.read(reader: (FileReader) -> R): R = FileReader(this).use { reader(it) }

inline fun File.write(write: (FileWriter) -> Unit) {
	if (this.parentFile.exists().not()) Files.createParentDirs(this)
	FileWriter(this).use(write)
}

inline fun <T : Any> File.readJson(to: KClass<T>, onDoesNotExist: () -> T): T {
	if (this.exists().not()) return onDoesNotExist()
	return this.read { gson.fromJson(it, to.java) }
}

fun <T : Any> File.readJson(to: KClass<T>, defaultValue: T): T {
	if (this.exists().not()) return defaultValue.apply { writeJsonTo(this@readJson) }
	return this.read { gson.fromJson(it, to.java) }
}