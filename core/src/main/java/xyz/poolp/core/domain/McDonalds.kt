package xyz.poolp.core.domain

data class McDonalds(
    val identifier: String,
    val formattedAddress: String,
    val shortFormattedAddress: String,
    val latitude:Double,
    val longitude:Double,
    val locality: String
)
