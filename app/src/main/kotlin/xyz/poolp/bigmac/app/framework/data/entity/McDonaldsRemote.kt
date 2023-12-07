package xyz.poolp.bigmac.app.framework.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class McDonaldsRemote(
    val places: List<McDonaldsRemotePlaces>,
)
@Serializable
data class McDonaldsRemotePlaces(
    val id: String
)