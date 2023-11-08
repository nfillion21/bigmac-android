package xyz.poolp.bigmac.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.poolp.bigmac.bigmac.BigMacRoute
import xyz.poolp.bigmac.navigation.Destinations.BIG_MAC_ROUTE
import xyz.poolp.bigmac.navigation.Destinations.WELCOME_ROUTE
import xyz.poolp.bigmac.welcome.WelcomeRoute

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val BIG_MAC_ROUTE = "bigmac"
}

@Composable
fun BigMacNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = WELCOME_ROUTE,
    ) {
        composable(WELCOME_ROUTE) {
            WelcomeRoute(
                startRoad = {
                    navController.navigate(BIG_MAC_ROUTE)
                },
            )
        }

        composable(BIG_MAC_ROUTE) {
            BigMacRoute(
                onNavUp = navController::navigateUp,
            )
        }
    }
}
