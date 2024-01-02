package xyz.poolp.bigmac.framework.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class McDonaldsPostBody(
    val textQuery: String,
    val maxResultCount: Int,
    val locationBias: McDonaldsPostBodyLocationBias
)
@Serializable
data class McDonaldsPostBodyLocationBias(
    val circle: McDonaldsPostBodyCircle,
)

@Serializable
data class McDonaldsPostBodyCircle(
    val center: McDonaldsPostBodyLocation,
    val radius: Double
)

@Serializable
data class McDonaldsPostBodyLocation(
    val latitude: Double,
    val longitude: Double
)

/*
{
  "textQuery" : "McDonald''s",
  "maxResultCount": 10,
  "locationBias": {
    "circle": {
      "center": {"latitude": 49.145964299999996, "longitude": 2.4415903},
      "radius": 50000.0
    }
  }
}
 */