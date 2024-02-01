import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("kitchensink.base-conventions")
    // id("kitchensink.publishing-conventions")
    alias(libs.plugins.run.paper)
    alias(libs.plugins.pluginyml.paper)
    alias(libs.plugins.shadow)
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(projects.kitchensinkCommon)
    implementation(libs.cloud.paper)
    implementation(libs.cloud.translations.bukkit)

    compileOnlyApi(libs.paper)
}

tasks {
    runServer {
        minecraftVersion("1.20.4")
    }

    shadowJar {
        dependencies {
            exclude {
                it.moduleGroup == "com.google.guava"
            }
        }

        relocate("org.incendo.cloud", "org.incendo.kitchensink.cloud")
        relocate("com.google.inject", "org.incendo.kitchensink.google")
        relocate("io.leangen.geantyref", "org.incendo.kitchensink.genatyref")
        relocate("jakarta.inject", "org.incendo.kitchensink.inject")
        relocate("org.aopalliance", "org.incendo.kitchensink.aopalliance")
    }

    build {
        dependsOn(shadowJar)
    }
}

paper {
    main = "org.incendo.kitchensink.paper.PaperKitchenSink"
    apiVersion = "1.20"
    authors = listOf("Citymonstret")
    prefix = "KitchenSink"

    permissions {
        register("kitchensink.command.utility.gamemode") {
            defaultPermission = BukkitPluginDescription.Permission.Default.OP
        }
        register("kitchensink.command.utility.gamemode.other") {
            defaultPermission = BukkitPluginDescription.Permission.Default.OP
        }
    }
}
