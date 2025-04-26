plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.kotlinKapt)
  alias(libs.plugins.kotlinPluginSerialization)
  alias(libs.plugins.shadow)
}

val pluginVersion: String by project
val kotlinVersion = libs.versions.kotlin.get()
val velocityVersion = libs.versions.velocity.get()

group = "io.github.morapowered"
val snapshotQualifier = project.findProperty("isSnapshot")?.toString()?.toBoolean()?.let { if (it) "-SNASPHOT" else "" } ?: ""
version = "$pluginVersion+kotlin.$kotlinVersion$snapshotQualifier"

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
  mavenLocal()
}

dependencies {
  compileOnly(libs.velocityApi)
  kapt(libs.velocityApi)

  implementation(libs.bundles.kotlinLibraries)
  implementation(libs.adventureExtraKotlin)
  implementation(libs.configurateExtraKotlin)
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8

  withSourcesJar()
  withJavadocJar()
}

tasks {
  build {
    dependsOn(shadowJar)
  }

  withType<JavaCompile> {
    options.release.set(17)
    options.encoding = "UTF-8"
  }
}

