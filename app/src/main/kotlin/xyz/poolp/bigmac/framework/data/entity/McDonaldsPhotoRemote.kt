package xyz.poolp.bigmac.framework.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class McDonaldsPhotoRemote(
    val name: String,
    val photoUri: String
)