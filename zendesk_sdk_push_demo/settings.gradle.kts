pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://zendesk.jfrog.io/artifactory/repo")
        }
    }
}
rootProject.name = "zendesk_sdk_push_demo"
include(":app")
