package pgm.poolp.core.domain

import android.location.Location

data class McDonalds(
    val identifier: String,
    val address: String,
    val location:Location,
    val city: String
)
