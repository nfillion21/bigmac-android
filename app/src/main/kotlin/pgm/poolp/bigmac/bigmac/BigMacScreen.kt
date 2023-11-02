package pgm.poolp.bigmac.bigmac

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pgm.poolp.bigmac.R
import pgm.poolp.bigmac.util.supportWideScreen
import pgm.poolp.bigmac.viewmodels.BigMacScreenData

@OptIn(ExperimentalMaterial3Api::class)
// Scaffold is experimental in m3
@Composable
fun BigMacScreen(
    bigMacScreenData: BigMacScreenData,
    onClosePressed: () -> Unit,
    onPreviousPressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    Surface(modifier = Modifier.supportWideScreen()) {
        val topAppBarState = rememberTopAppBarState()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
        Scaffold(
            topBar = {
                BigMacAppBar(
                    title = "Lamorlaye",
                    scrollBehavior = scrollBehavior,
                    onClosePressed = onClosePressed,
                    onBackPressed = onClosePressed
                )
            },
            content = content,
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BigMacAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior?,
    onClosePressed: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.mcdonalds_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(percent = 15))
                        .size(36.dp)
                )
                Text(
                    text = stringResource(R.string.mcdonalds_in, title),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.cd_navigate_up),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            IconButton(
                onClick = onClosePressed,
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.close),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}
