object Dependencies {
    val coreAndroidX by lazy {"androidx.core:core-ktx:${Versions.coreAndroidX}"}
    val lifeCycleRuntime by lazy {"androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycleRuntime}"}
    val viewModelCompose by lazy {"androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifeCycleRuntime}"}
    val viewModelRuntimeCompose by lazy {"androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifeCycleRuntime}"}
    val activityCompose by lazy {"androidx.activity:activity-compose:${Versions.activityCompose}"}
    val composeBom by lazy {"androidx.compose:compose-bom:${Versions.composeBom}"}
    val composeUi by lazy {"androidx.compose.ui:${Versions.composeUi}"}
    val composeGraphics by lazy {"androidx.compose.ui:${Versions.composeGraphics}"}
    val composeUiToolingPreview by lazy {"androidx.compose.ui:${Versions.composeUiToolingPreview}"}
    val composeMaterial3 by lazy {"androidx.compose.material3:${Versions.composeMaterial3}"}
    val composeMaterial by lazy {"androidx.compose.material:material:${Versions.composeMaterial}"}

    val mapUtils by lazy {"com.google.maps.android:android-maps-utils:${Versions.mapUtils}"}

    val junit by lazy {"junit:junit:${Versions.junit}"}
    val truth by lazy {"com.google.truth:truth:${Versions.truth}"}
    val mockito by lazy {"org.mockito:mockito-core:${Versions.mockito}"}
    val coreTest by lazy {"androidx.test:core-ktx:${Versions.coreTest}"}
    val androidTestJUnit by lazy {"androidx.test.ext:junit:${Versions.androidTestJUnit}"}
    val androidTestEspresso by lazy {"androidx.test.espresso:espresso-core:${Versions.androidTestEspresso}"}
    val androidTestComposeBom by lazy {"androidx.compose:compose-bom:${Versions.androidTestComposeBom}"}
    val androidTestComposeUi by lazy {"androidx.compose.ui:${Versions.androidTestComposeUi}"}
    val debugComposeUiTooling by lazy {"androidx.compose.ui:${Versions.debugComposeUiTooling}"}
    val debugComposeUiTestManifest by lazy {"androidx.compose.ui:${Versions.debugComposeUiTestManifest}"}

    // Hilt
    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }

    val hiltNavigationCompose by lazy {"androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationComposeVersion}"}
    val navigationCompose by lazy {"androidx.navigation:navigation-compose:${Versions.navigationComposeVersion}"}

    // Ktor
    val ktorAndroidClient by lazy { "io.ktor:ktor-client-android:${Versions.ktor}" }
    val ktorSerializationJSON by lazy { "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}" }
    val ktorContentNegotiation by lazy { "io.ktor:ktor-client-content-negotiation:${Versions.ktor}" }
    val ktorClientLogging by lazy { "io.ktor:ktor-client-logging:${Versions.ktor}" }
    val ktorClientMock by lazy { "io.ktor:ktor-client-mock:${Versions.ktor}" }

    // Coil
    val coil by lazy {"io.coil-kt:coil-compose:${Versions.coil}"}
}