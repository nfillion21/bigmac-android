package xyz.poolp.bigmac.app.framework.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class McDonaldsRemote(
    val places: List<McDonaldsRemotePlaces>,
)
@Serializable
data class McDonaldsRemotePlaces(
    val id: String,
    val formattedAddress: String,
    val location: McDonaldsRemoteLocation,
    val shortFormattedAddress: String,
    val addressComponents: List<McDonaldsRemoteAddressComponent>
)

@Serializable
data class McDonaldsRemoteAddressComponent(
    val longText: String,
    val shortText: String,
    val types: List<String>,
    val languageCode: String
)

@Serializable
data class McDonaldsRemoteLocation(
    val latitude: Double,
    val longitude: Double
)