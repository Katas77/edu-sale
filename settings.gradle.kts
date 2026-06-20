pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "edu-sale"

include(":app")
include(":domain")
include(":data")

include(":core:common")
include(":core:network")
include(":core:database")

include(":core:common")
include(":feature:auth")
include(":feature:courses")
include(":feature:favorites")
include(":feature:account")
