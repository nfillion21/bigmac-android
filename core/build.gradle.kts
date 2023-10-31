plugins {
    id("java-library")
    kotlin("jvm") version embeddedKotlinVersion
}
dependencies {
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
