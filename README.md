# Big Mac
<https://play.google.com/store/apps/details?id=xyz.poolp.bigmac>

A Jetpack compose app to navigate from McDonald's to McDonald's using Google's [Places API][0].

Libraries and components used
--------------
* [Material Design 3][1] - Light and dark mode supported with MaterialTheme.
* [Hilt][2] for [dependency injection][3]
* [Ktor][4] - Ktor for API requests. Using [MockEngine][5] for testing.
* [kotlinx.serialization][6] - Using it to convert JSON objects into data class objects.
* [Coil][7] - Used to load images from network.
* [Clean architecture][8] - Built following Clean Architecture for Android.

[0]: https://developers.google.com/maps/documentation/places/web-service/op-overview
[1]: https://developer.android.com/jetpack/compose/themes/material3
[2]: https://developer.android.com/training/dependency-injection/hilt-android
[3]: https://developer.android.com/training/dependency-injection
[4]: https://ktor.io/
[5]: https://ktor.io/docs/http-client-testing.html
[6]: https://github.com/Kotlin/kotlinx.serialization
[7]: https://coil-kt.github.io/coil/compose/
[8]: https://developer.android.com/topic/architecture
