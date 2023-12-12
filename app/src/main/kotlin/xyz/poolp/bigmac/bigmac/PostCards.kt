package xyz.poolp.bigmac.bigmac

import android.icu.text.DecimalFormat
import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import xyz.poolp.bigmac.R
import xyz.poolp.core.domain.McDonalds

@Composable
fun McDonaldsDistance(
    mcDo: McDonalds,
    currentMcDo: McDonalds,
    step: Int,
    modifier: Modifier = Modifier
) {
    val locationCurrentMcDo = Location(null)
    locationCurrentMcDo.latitude = currentMcDo.latitude
    locationCurrentMcDo.longitude = currentMcDo.longitude

    val locationMcDo = Location(null)
    locationMcDo.latitude = mcDo.latitude
    locationMcDo.longitude = mcDo.longitude

    val distance = locationCurrentMcDo.distanceTo(locationMcDo) / 1000.0f
    val dec = DecimalFormat("#,##0.000")

    Row(modifier) {
        Text(
            text = stringResource(
                id = R.string.distance_km,
                formatArgs = arrayOf(
                    dec.format(distance), step
                )
            ),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun McDonaldsImage(mcdonalds: McDonalds, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.mcdonalds_logo),
        contentDescription = null, // decorative
        modifier = modifier
            .size(40.dp, 40.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun McDonaldsTitle(mcdonalds: McDonalds) {
    Text(
        text = mcdonalds.formattedAddress,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun McDoCardNearby(
    mcdo: McDonalds,
    currentMcDo:McDonalds,
    step: Int,
    roadToMcdo: (McDonalds) -> Unit
) {
    Row(
        Modifier
            .clickable(onClick = { roadToMcdo(mcdo) })
    ) {
        McDonaldsImage(
            mcdonalds = mcdo,
            modifier = Modifier.padding(16.dp)
        )
        Column(
            Modifier
                .weight(1f)
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.based_in_locality, mcdo.locality.uppercase()),
                style = MaterialTheme.typography.labelMedium
            )
            McDonaldsTitle(mcdonalds = mcdo)
            McDonaldsDistance(
                mcDo = mcdo,
                currentMcDo = currentMcDo,
                step = step,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}