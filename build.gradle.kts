import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    kotlin("jvm") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.camdenorrb"
version = "2.0.0"

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

    //compileOnly("com.boydti:fawe-api:latest")
    //compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.0.1")
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")

    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.sxtanna.korm:Korm:+")
    implementation("com.github.camdenorrb:KCommons:V1.0.8")
    implementation("com.github.MiniMineCraft:MiniBus:1.2.9")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}


tasks.withType<ShadowJar> {
    //minimize()
    archiveFileName.set("KPortals.jar")
}

/*
task("relocateShadowJar", ConfigureShadowRelocation::class) {
    target = tasks.shadowJar.get()
    prefix = "libs"
}

tasks.shadowJar.get().dependsOn += tasks.named("relocateShadowJar")*/
//tasks.shadowJar.get().dependsOn += tasks.relo

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.apiVersion = "1.3"
}
