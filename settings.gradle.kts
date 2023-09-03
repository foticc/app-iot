pluginManagement {
    repositories {
        maven { setUrl("https://maven.aliyun.com/nexus/content/groups/public/") }
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}


rootProject.name = "NavhostScreenApp"
include(":app")
 