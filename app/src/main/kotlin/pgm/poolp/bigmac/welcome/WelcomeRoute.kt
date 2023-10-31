package pgm.poolp.bigmac.welcome

import androidx.compose.runtime.Composable
import pgm.poolp.bigmac.screens.WelcomeScreen

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
