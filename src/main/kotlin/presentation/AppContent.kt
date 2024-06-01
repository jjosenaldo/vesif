package presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import presentation.screens.project_selected.ProjectSelectedScreen
import presentation.select_project.SelectProjectScreen

enum class AppScreen {
    SelectProject,
    ProjectSelected
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
        composable(route = AppScreen.ProjectSelected.name) {
            ProjectSelectedScreen()
        }
    }
}