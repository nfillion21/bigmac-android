package xyz.poolp.bigmac.welcome

import androidx.compose.runtime.Composable
import xyz.poolp.bigmac.screens.WelcomeScreen

@Composable
fun WelcomeRoute(
    startRoad: () -> Unit,
) {
    WelcomeScreen(
        onSignInAsGuest = {
            startRoad()
        },
    )
}
