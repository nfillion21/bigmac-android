package xyz.poolp.bigmac.bigmac

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
    mcdonalds: McDonalds,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Text(
            text = stringResource(
                id = R.string.distance_km,
                formatArgs = arrayOf(
                    1
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
        text = mcdonalds.shortFormattedAddress,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun McDonaldsCardNearby(mcdonalds: McDonalds, roadToMcDonalds: (String) -> Unit) {
    Row(
        Modifier
            .clickable(onClick = { roadToMcDonalds(mcdonalds.identifier) })
    ) {
        McDonaldsImage(
            mcdonalds = mcdonalds,
            modifier = Modifier.padding(16.dp)
        )
        Column(
            Modifier
                .weight(1f)
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.based_in_locality, mcdonalds.locality.uppercase()),
                style = MaterialTheme.typography.labelMedium
            )
            McDonaldsTitle(mcdonalds = mcdonalds)
            McDonaldsDistance(
                mcdonalds = mcdonalds,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}