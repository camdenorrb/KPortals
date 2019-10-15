package me.camdenorrb.kportals.ext

import com.google.gson.GsonBuilder
import java.io.File


// Json for legacy support


@PublishedApi
internal val gson by lazy {
    GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
}


fun Any.toJson(): String {
    return gson.toJson(this)
}

fun Any.writeJsonTo(file: File) {
    file.parentFile?.mkdirs()
    file.writeText(this.toJson())
}


inline fun <reified T : Any> String.readJson(): T {
    return gson.fromJson(this, T::class.java)
}

inline fun <reified T : Any> File.readJson(onDoesNotExist: () -> T): T {
    return if (this.exists().not()) onDoesNotExist()
    else this.reader().use { gson.fromJson(it, T::class.java) }
}

inline fun <reified T : Any> File.readJson(defaultValue: T): T {
    if (this.exists().not()) return defaultValue.apply { writeJsonTo(this@readJson) }
    return this.reader().use { gson.fromJson(it, T::class.java) }
}