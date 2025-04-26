plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.kotlinPluginSerialization)
    alias(libs.plugins.shadow)
    alias(libs.plugins.versioning)
    alias(libs.plugins.blossom)
}

val pluginVersion: String by project
val kotlinVersion = libs.versions.kotlin.get()
val velocityVersion = libs.versions.velocity.get()
val isSnapshot = project.findProperty("isSnapshot")?.toString()?.toBoolean() ?: false
val cleanVersion = "$pluginVersion+kotlin.$kotlinVersion"
version = if (isSnapshot) {
    "$cleanVersion-${versioning.info.branch.substringAfter("/")}-${versioning.info.build}"
} else {
    cleanVersion
}

base {
    archivesName.set("velocity-language-kotlin")
}

dependencies {
    compileOnly(libs.velocityApi)
    kapt(libs.velocityApi)

    implementation(project(":api"))
}

sourceSets {
    main {
        blossom {
            kotlinSources {
                property("version", cleanVersion)
                property("isSnapshot", isSnapshot.toString())
                property("branch", versioning.info.branch)
                property("build", versioning.info.build)
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    withSourcesJar()
    withJavadocJar()
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveClassifier.set("")
    }

    withType<JavaCompile> {
        options.release.set(17)
        options.encoding = "UTF-8"
    }
}