package de.haukeschebitz.pokeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.haukeschebitz.pokeapp.presentation.MainScreen
import de.haukeschebitz.pokeapp.presentation.MainScreenViewModel
import de.haukeschebitz.pokeapp.ui.theme.PokeAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            PokeAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val mainScreenViewModel: MainScreenViewModel by viewModel()
                    val state = mainScreenViewModel.uiState.collectAsStateWithLifecycle().value
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        state = state,
                    )
                }
            }
        }
    }
}
