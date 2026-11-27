plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

// Módulo de lógica del negocio: no depende de Compose ni de ninguna interfaz gráfica.
kotlin {
    jvm()
    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
