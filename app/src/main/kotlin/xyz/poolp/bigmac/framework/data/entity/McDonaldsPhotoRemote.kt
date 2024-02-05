package xyz.poolp.bigmac.framework.data.entity

import kotlinx.serialization.Serializable
import xyz.poolp.core.domain.McDonaldsPhoto

@Serializable
data class McDonaldsPhotoRemote(
    val name: String,
    val photoUri: String
)

fun McDonaldsPhotoRemote.mapToMcDonaldsPhoto () =
    McDonaldsPhoto (
        url = photoUri
    )
