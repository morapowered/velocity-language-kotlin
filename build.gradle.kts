plugins {
    alias(libs.plugins.nexusPublishPlugin)
    signing
}



subprojects {
    group = "io.github.morapowered"

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        mavenLocal()
    }
}


nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
            username.set(project.findProperty("sonatype.username")?.toString() ?: System.getenv("SONATYPE_USERNAME"))
            password.set(project.findProperty("sonatype.password")?.toString() ?: System.getenv("SONATYPE_PASSWORD"));
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
}