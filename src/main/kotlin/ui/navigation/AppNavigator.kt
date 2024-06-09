package ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.screens.AppScreenId
import ui.screens.project_selected.ProjectSelectedSubScreenId
import ui.screens.project_selected.sub_screens.assertions.AssertionsViewModel
import ui.screens.project_selected.sub_screens.failed_assertion.FailedAssertionViewModel
import ui.screens.select_project.ProjectViewModel
import verifier.model.common.AssertionRunResult
import verifier.model.common.AssertionType

class AppNavigator(private val navController: NavHostController) : KoinComponent {
    private val failedAssertionsViewModel: FailedAssertionViewModel by inject()
    private val assertionsViewModel: AssertionsViewModel by inject()
    private val projectViewModel: ProjectViewModel by inject()
    private val currentScreen: AppScreenId
        get() = AppScreenId.entries.firstOrNull { it.name == navController.currentBackStackEntry?.destination?.route }
            ?: AppScreenId.SelectProject

    var currentProjectSelectedSubScreen by mutableStateOf(ProjectSelectedSubScreenId.None)
        private set

    fun navBack() {
        if (currentScreen != AppScreenId.ProjectSelected) return

        when (currentProjectSelectedSubScreen) {
            ProjectSelectedSubScreenId.SelectCircuit -> navToSelectProject()
            ProjectSelectedSubScreenId.Assertions -> navToSelectCircuit()
            ProjectSelectedSubScreenId.FailedAssertion -> navToProjectSelectedSubScreen(ProjectSelectedSubScreenId.Assertions)
            ProjectSelectedSubScreenId.None -> {}
        }

    }

    fun navToSelectCircuit() {
        navToProjectSelectedSubScreen(ProjectSelectedSubScreenId.SelectCircuit)
    }

    fun navToFailedAssertions(results: List<AssertionRunResult>) {
        failedAssertionsViewModel.setup(results)
        navToProjectSelectedSubScreen(ProjectSelectedSubScreenId.FailedAssertion)
    }

    fun navToAssertions(types: List<AssertionType>) {
        assertionsViewModel.setAssertionsFromTypes(types)
        navToProjectSelectedSubScreen(ProjectSelectedSubScreenId.Assertions)
    }

    private fun navToProjectSelectedSubScreen(destination: ProjectSelectedSubScreenId) {
        navToScreen(AppScreenId.ProjectSelected)
        currentProjectSelectedSubScreen = destination
    }

    private fun navToSelectProject() {
        projectViewModel.reset()
        navToScreen(AppScreenId.SelectProject)
    }

    private fun navToScreen(screen: AppScreenId) {
        if (currentScreen != screen) {
            navController.navigate(screen.name)
        }
    }

}