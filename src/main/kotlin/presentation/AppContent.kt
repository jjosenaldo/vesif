package presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import presentation.assertions.AssertionsScreen
import presentation.select_circuit.SelectCircuitScreen
import presentation.select_project.SelectProjectScreen

enum class AppScreen {
    SelectProject,
    SelectCircuit,
    Assertions
}

@Composable
fun AppContent() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreen.SelectProject.name
    ) {
        composable(route = AppScreen.SelectProject.name) {
            SelectProjectScreen(navController)
        }
        composable(route = AppScreen.SelectCircuit.name) {
            SelectCircuitScreen(navController)
        }
        composable(route = AppScreen.Assertions.name) {
            AssertionsScreen()
        }
    }
}