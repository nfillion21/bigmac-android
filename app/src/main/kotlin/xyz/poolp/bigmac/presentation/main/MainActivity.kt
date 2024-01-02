package xyz.poolp.bigmac.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import xyz.poolp.bigmac.presentation.main.navigation.BigMacNavHost
import xyz.poolp.bigmac.ui.theme.BigMacTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BigMacTheme {
                BigMacNavHost()
            }
        }
    }
}