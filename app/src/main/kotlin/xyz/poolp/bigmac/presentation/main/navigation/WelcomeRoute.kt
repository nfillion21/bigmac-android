package xyz.poolp.bigmac.presentation.main.navigation

import androidx.compose.runtime.Composable
import xyz.poolp.bigmac.presentation.main.screens.WelcomeScreen

@Composable
fun WelcomeRoute(
    startRoad: () -> Unit,
) {
    WelcomeScreen(
        onStartRoad = {
            startRoad()
        },
    )
}
