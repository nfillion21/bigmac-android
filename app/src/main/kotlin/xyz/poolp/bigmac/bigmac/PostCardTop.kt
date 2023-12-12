package xyz.poolp.bigmac.bigmac

import android.icu.text.DecimalFormat
import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.poolp.bigmac.R
import xyz.poolp.core.domain.McDonalds

@Composable
fun McDonaldsCardTop(
    mcDo: McDonalds,
    lamorlayeMcDo: McDonalds,
    step:Int,
    modifier: Modifier = Modifier
) {
    val typography = MaterialTheme.typography
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val imageModifier = Modifier
            //.heightIn(min = 180.dp)
            //.fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
        Image(
            painter = painterResource(R.drawable.current_mcdonalds),
            contentDescription = null, // decorative
            modifier = imageModifier,
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = mcDo.formattedAddress,
            style = typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "McDonald\'s #$step",
            style = typography.labelLarge,
        )

        if (lamorlayeMcDo != mcDo) {
            val locationLamorlayeMcDo = Location(null)
            locationLamorlayeMcDo.latitude = lamorlayeMcDo.latitude
            locationLamorlayeMcDo.longitude = lamorlayeMcDo.longitude

            val locationMcdo = Location(null)
            locationMcdo.latitude = mcDo.latitude
            locationMcdo.longitude = mcDo.longitude

            val distance = locationLamorlayeMcDo.distanceTo(locationMcdo) / 1000.0f
            val dec = DecimalFormat("#,##0.000")

            Text(
                text = stringResource(
                    id = R.string.distance_from_lamorlaye_km,
                    formatArgs = arrayOf(
                        dec.format(distance)
                    )
                ),
                style = typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}