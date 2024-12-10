import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    // TODO(td): move this into a separate variable
    implementation(files("C:\\Program Files\\FDR\\bin\\fdr.jar"))
    implementation("com.darkrockstudios:mpfilepicker:3.1.0")
    implementation(platform("io.insert-koin:koin-bom:3.5.6"))
    implementation("io.insert-koin:koin-core:3.5.6")
    implementation("io.insert-koin:koin-compose:1.0.5")
    implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
    implementation(compose.material3)
    implementation("androidx.compose.material:material-icons-extended:1.6.8")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "circuit-verifier-v2"
            packageVersion = "1.0.0"
            modules("jdk.unsupported")

            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))

        }
    }
}
