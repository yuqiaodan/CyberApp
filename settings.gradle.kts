pluginManagement {
    repositories {
        // 添加阿里云镜像仓库
        maven {
            url = uri("https://maven.aliyun.com/repository/central")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/jcenter")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
        }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // 添加阿里云镜像仓库
        maven {
            url = uri("https://maven.aliyun.com/repository/central")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/jcenter")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "CyberApp"
include(":app")
 