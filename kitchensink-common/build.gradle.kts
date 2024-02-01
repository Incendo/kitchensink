plugins {
    id("kitchensink.base-conventions")
    // id("kitchensink.publishing-conventions")
}

dependencies {
    api(libs.cloud.core)
    api(libs.guice)
    api(libs.jakarta.inject)
    api(libs.cloud.translations.core)

    compileOnlyApi(libs.slf4j)
    compileOnlyApi(libs.adventure)
}
