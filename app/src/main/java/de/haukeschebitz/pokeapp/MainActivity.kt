package de.haukeschebitz.pokeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import de.haukeschebitz.pokeapp.presentation.detail.DetailScreen
import de.haukeschebitz.pokeapp.presentation.detail.DetailScreenViewModel
import de.haukeschebitz.pokeapp.presentation.main.MainScreen
import de.haukeschebitz.pokeapp.presentation.main.MainScreenActions
import de.haukeschebitz.pokeapp.presentation.main.MainScreenViewModel
import de.haukeschebitz.pokeapp.ui.navigation.Route
import de.haukeschebitz.pokeapp.ui.theme.PokeAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val backstack = rememberNavBackStack(Route.Main)

            PokeAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavDisplay(
                        modifier = Modifier.fillMaxSize(),
                        backStack = backstack,
                        onBack = { backstack.removeLastOrNull() },
                        entryDecorators = listOf(
                            rememberSavedStateNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        entryProvider = entryProvider {
                            entry<Route.Main> {
                                val mainScreenViewModel: MainScreenViewModel by viewModel()
                                val state = mainScreenViewModel.uiState.collectAsStateWithLifecycle().value
                                MainScreen(
                                    state = state,
                                    actions = MainScreenActions(
                                        onShowDetailScreen = {
                                            mainScreenViewModel.onShowDetailScreen(it)
                                            backstack.add(Route.Detail(it))
                                        }
                                    )
                                )
                            }

                            entry<Route.Detail> {
                                val detailScreenViewModel: DetailScreenViewModel by viewModel { parametersOf(it.eventId) }
                                val state = detailScreenViewModel.uiState.collectAsStateWithLifecycle().value
                                DetailScreen(
                                    state = state,
                                    onScreenShown = { detailScreenViewModel.onScreenShown() }
                                )
                            }
                        },
                    )

                }
            }
        }
    }
}
