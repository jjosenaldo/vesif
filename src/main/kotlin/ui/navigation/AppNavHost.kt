package ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.core.context.loadKoinModules
import ui.di.navModule
import ui.screens.AppScreenId
import ui.screens.project_selected.ProjectSelectedScreen
import ui.screens.select_project.SelectProjectScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController().also {
        loadKoinModules(navModule(it))
    }

    NavHost(
        navController = navController,
        startDestination = AppScreenId.SelectProject.name
    ) {
        composable(route = AppScreenId.SelectProject.name) {
            SelectProjectScreen()
        }
        composable(route = AppScreenId.ProjectSelected.name) {
            ProjectSelectedScreen()
        }
    }
}