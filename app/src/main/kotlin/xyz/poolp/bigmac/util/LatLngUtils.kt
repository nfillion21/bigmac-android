package xyz.poolp.bigmac.util

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

enum class LocationDirection(val description:String) {
    UNKNOWN("unknown"),
    NORTH("north"),
    NORTH_EAST("north-east"),
    EAST("east"),
    SOUTH_EAST("south-east"),
    SOUTH("south"),
    SOUTH_WEST("south-west"),
    WEST("west"),
    NORTH_WEST("north-west")
}
fun LatLng.direction(latlng: LatLng): LocationDirection {

    val delta = 22.5
    var direction: LocationDirection = LocationDirection.UNKNOWN
    val heading = SphericalUtil.computeHeading(this, latlng)
    if (heading >= 0 && heading < delta || heading < 0 && heading >= -delta) {
        direction = LocationDirection.NORTH
    } else if (heading >= delta && heading < 90 - delta) {
        direction = LocationDirection.NORTH_EAST
    } else if (heading >= 90 - delta && heading < 90 + delta) {
        direction = LocationDirection.EAST
    } else if (heading >= 90 + delta && heading < 180 - delta) {
        direction = LocationDirection.SOUTH_EAST
    } else if (heading >= 180 - delta || heading <= -180 + delta) {
        direction = LocationDirection.SOUTH
    } else if (heading >= -180 + delta && heading < -90 - delta) {
        direction = LocationDirection.SOUTH_WEST
    } else if (heading >= -90 - delta && heading < -90 + delta) {
        direction = LocationDirection.WEST
    } else if (heading >= -90 + delta && heading < -delta) {
        direction = LocationDirection.NORTH_WEST
    }
    return direction
}