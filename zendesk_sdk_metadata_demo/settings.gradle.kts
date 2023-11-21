pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://zendesk.jfrog.io/artifactory/repo")
    }
}

rootProject.name = "Zendesk_sdk_metadata_demo"
include(":app")
 