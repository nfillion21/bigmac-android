package xyz.poolp.bigmac.presentation.main.screens

import android.icu.text.DecimalFormat
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.google.android.gms.maps.model.LatLng
import xyz.poolp.bigmac.R
import xyz.poolp.bigmac.util.direction
import xyz.poolp.core.domain.McDonalds

@Composable
fun McDonaldsCardTop(
    state: BigMacUIState,
    mcDo: McDonalds,
    lamorlayeMcDo: McDonalds,
    retryMcDonaldsPhoto: () -> Unit,
    step: Int
) {
    val typography = MaterialTheme.typography
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    shape = MaterialTheme.shapes.medium
                )
        )
                {
                    val imageModifier = Modifier
                        .fillMaxSize()
                        .clip(shape = MaterialTheme.shapes.medium)

                    mcDo.photo?.let {
                        SubcomposeAsyncImage(
                            model = it.url,
                            loading = {
                                LinearProgressIndicator(modifier = Modifier.fillMaxSize()
                                    .wrapContentSize(Alignment.Center))
                            },
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = imageModifier
                        )
                    } ?:

                    when (state.mcDonaldsPhotoViewState) {

                        is McDonaldsPhotoViewState.Failure -> {
                            FilledTonalButton(
                                onClick = retryMcDonaldsPhoto,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .align(Alignment.Center)
                            ) {
                                Text(text = stringResource(id = R.string.an_error_occurred))
                            }
                        }

                        is McDonaldsPhotoViewState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.fillMaxSize()
                                .wrapContentSize(Alignment.Center))
                        }

                        else -> {}
                    }
                }

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

        if (lamorlayeMcDo.identifier != mcDo.identifier) {

            val locationLamorlayeMcDo = Location(null)
            locationLamorlayeMcDo.latitude = lamorlayeMcDo.latitude
            locationLamorlayeMcDo.longitude = lamorlayeMcDo.longitude

            val locationMcdo = Location(null)
            locationMcdo.latitude = mcDo.latitude
            locationMcdo.longitude = mcDo.longitude

            val distance = locationLamorlayeMcDo.distanceTo(locationMcdo) / 1000.0f
            val dec = DecimalFormat("#,##0.000")

            val latLngLamorlayeMcDo = LatLng(lamorlayeMcDo.latitude, lamorlayeMcDo.longitude)
            val latLngMcDo = LatLng(mcDo.latitude, mcDo.longitude)
            val locationDirection = latLngLamorlayeMcDo.direction(latLngMcDo)

            Text(
                text = stringResource(
                    id = R.string.distance_from_lamorlaye_km,
                    formatArgs = arrayOf(
                        dec.format(distance), locationDirection.description
                    )
                ),
                style = typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}