
import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    kotlin("jvm") version "1.3.50"
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

group = "me.camdenorrb"
version = "1.0.0"

repositories {

    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/groups/public/") {
        name = "Spigot"
    }

    maven("https://jitpack.io") {
        name = "Jitpack"
    }

    maven("https://ci.athion.net/job/FastAsyncWorldEdit-Breaking/ws/mvn") {
        name = "FAWE"
    }
}

dependencies {

    compileOnly("com.boydti:fawe-api:latest")
    //compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.0.1")
    compileOnly("org.spigotmc:spigot-api:1.14.4-R0.1-SNAPSHOT")

    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.MiniMineCraft:MiniBus:V1.2.7")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<ShadowJar> {
    minimize()
    //relocate("org.jetbrains", "libs.org.jetbrains")
}

tasks.withType<ConfigureShadowRelocation> {
    target = tasks.shadowJar.get()
    prefix = "KPortals"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.apiVersion = "1.3"
    kotlinOptions.jvmTarget = "1.8"
}
