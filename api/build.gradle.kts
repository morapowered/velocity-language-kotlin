plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.kotlinPluginSerialization)
  `java-library`
  `maven-publish`
}

val pluginVersion: String by project
val kotlinVersion = libs.versions.kotlin.get()
val velocityVersion = libs.versions.velocity.get()
val snapshotQualifier =
  project.findProperty("isSnapshot")?.toString()?.toBoolean()?.let { if (it) "-SNAPSHOT" else "" }
    ?: ""
version = "$pluginVersion+kotlin.$kotlinVersion$snapshotQualifier"

repositories {
  mavenCentral()
}

dependencies {
  compileOnlyApi(libs.velocityApi)
  annotationProcessor(libs.velocityApi)

  api(libs.bundles.kotlinLibraries)
  api(libs.adventureExtraKotlin)
  api(libs.configurateExtraKotlin)
}

publishing {
  publications {
    create<MavenPublication>(project.name) {
      from(components["java"])

      group = "com.velocitypowered"
      artifactId = "velocity-language-kotlin"
      version = project.version.toString()

      pom {
        name.set("velocity-language-kotlin")
        url.set("https://github.com/morapowered/velocity-language-kotlin")

        licenses {
          license {
            name.set("MIT License")
            url.set("https://www.opensource.org/licenses/mit-license.php")
          }
        }

        developers {
          developer {
            id.set("velocitycontributors")
            name.set("Velocity Contributors")
          }

          developer {
            id.set("eupedroosouza")
            name.set("Pedro Souza")
            email.set("66704494+eupedroosouza@users.noreply.github.com")
          }
        }

        scm {
          connection.set("scm:git:git://github.com/morapowered/velocity-language-kotlin.git")
          developerConnection.set("scm:git:ssh://github.com/morapowered/velocity-language-kotlin.git")
          url.set("https://github.com/morapowered/velocity-language-kotlin")
        }
      }
    }
  }
}
