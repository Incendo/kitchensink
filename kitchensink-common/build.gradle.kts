plugins {
    id("kitchensink.base-conventions")
    // id("kitchensink.publishing-conventions")
}

dependencies {
    api(libs.guice)
    api(libs.jakarta.inject)

    api(libs.cloud.core)
    api(libs.cloud.translations.core)
    api(libs.cloud.minecraft.extras)

    api(libs.configurate.yaml)
    api(libs.configurate.extra.guice)

    compileOnlyApi(libs.slf4j)
    compileOnlyApi(libs.adventure)
    compileOnlyApi(libs.minimessage)
}
