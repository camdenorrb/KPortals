package me.camdenorrb.kportals.extensions

import com.google.common.io.Files
import me.camdenorrb.kportals.gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Created by camdenorrb on 3/20/17.
 */

inline fun <R> File.read(reader: (FileReader) -> R): R = FileReader(this).use { reader(it) }

inline fun File.write(write: (FileWriter) -> Unit) {
	Files.createParentDirs(this)
	FileWriter(this).use(write)
}

inline fun <reified T : Any> File.readJson(onDoesNotExist: () -> T): T {
	if (this.exists().not()) return onDoesNotExist()
	return this.read { gson.fromJson(it, T::class.java) }
}

inline fun <reified T : Any> File.readJson(defaultValue: T): T {
	if (this.exists().not()) return defaultValue.apply { writeJsonTo(this@readJson) }
	return this.read { gson.fromJson(it, T::class.java) }
}