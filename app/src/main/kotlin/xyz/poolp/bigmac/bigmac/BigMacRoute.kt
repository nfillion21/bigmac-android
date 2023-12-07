package xyz.poolp.bigmac.bigmac

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.poolp.bigmac.viewmodels.BigMacViewModel

/**
 * Displays a [BigMacScreen] tied to the passed [BigMacViewModel]
 */
@Composable
fun BigMacRoute(
    onNavUp: () -> Unit,
) {
    val viewModel: BigMacViewModel = hiltViewModel()
    val bigMacScreenData = viewModel.bigMacScreenData

    BackHandler {
        if (!viewModel.onBackPressed()) {
            onNavUp()
        }
    }

    BigMacScreen(
        bigMacViewModel = viewModel,
        bigMacScreenData = bigMacScreenData,
        onClosePressed = { onNavUp() },
        onMcDoPressed = { viewModel.onMcDoPressed() },
        onPreviousPressed = { viewModel.onPreviousPressed() },
    )
}