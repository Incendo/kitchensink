plugins {
    id("org.incendo.cloud-build-logic")
    id("org.incendo.cloud-build-logic.spotless")
}

indra {
    checkstyle().set(libs.versions.checkstyle)

    javaVersions {
        minimumToolchain(17)
        target(17)
        testWith().set(setOf(17))
    }
}

cloudSpotless {
    ktlintVersion = libs.versions.ktlint
}

spotless {
    java {
        importOrderFile(rootProject.file(".spotless/kitchensink.importorder"))
    }
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatypeOssSnapshots"
        mavenContent {
            snapshotsOnly()
        }
    }
}

// Common dependencies.
dependencies {
    // external
    compileOnly(libs.immutables)
    annotationProcessor(libs.immutables)
    compileOnly(libs.auto.factory)
    annotationProcessor(libs.auto.factory)

    // test dependencies
    testImplementation(libs.jupiter.engine)
    testImplementation(libs.jupiter.params)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.jupiter)
    testImplementation(libs.truth)
}
