plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinPluginSerialization)
    `java-library`
    `maven-publish`
    signing
}

val pluginVersion: String by project
val kotlinVersion = libs.versions.kotlin.get()
val velocityVersion = libs.versions.velocity.get()
val snapshotQualifier =
    project.findProperty("isSnapshot")?.toString()?.toBoolean()?.let { if (it) "-SNAPSHOT" else "" } ?: ""
version = "$pluginVersion+kotlin.$kotlinVersion$snapshotQualifier"

base {
    archivesName.set("velocity-language-kotlin-${project.name}")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnlyApi(libs.velocityApi)

    api(libs.bundles.kotlinLibraries)
    api(libs.adventureExtraKotlin)
    api(libs.configurateExtraKotlin)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))

    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17

    withJavadocJar()
    withSourcesJar()
}

tasks {
    withType<JavaCompile> {
        options.release = 17
        options.encoding = "UTF-8"
    }
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])

            groupId = "io.morapowered"
            artifactId = "velocity-language-kotlin"
            version = project.version.toString()

            pom {
                name.set("velocity-language-kotlin")
                description.set("A fork of velocity-language-kotlin for Velocity programming in Kotlin")
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

signing {
    val signedKey = project.findProperty("signed.key")?.toString() ?: System.getenv("GPG_SECRET_KEY")
    val signedPassword = project.findProperty("signed.password")?.toString() ?: System.getenv("GPG_PASSPHRASE")

    if (signedKey != null && signedPassword != null) {
        useInMemoryPgpKeys(signedKey, signedPassword)
    } else {
        useGpgCmd()
    }

    sign(publishing.publications[project.name])
}

