package ui.di

import androidx.navigation.NavHostController
import csp_generator.generator.CspGenerator
import input.ClearsyCircuitParser
import input.ClearsyProjectParser
import org.koin.dsl.module
import ui.navigation.AppNavigator
import ui.screens.project_selected.sub_screens.assertions.AssertionsViewModel
import ui.screens.project_selected.sub_screens.failed_assertion.FailedAssertionViewModel
import ui.screens.project_selected.sub_screens.select_circuit.CircuitViewModel
import ui.screens.select_project.ProjectViewModel
import ui.window.AppWindowManager
import verifier.AssertionManager


fun navModule(navController: NavHostController) = module {
    single { AppNavigator(navController) }
}

fun appModule() = module {
    single { AppWindowManager() }
    single { CspGenerator() }
    single { ClearsyProjectParser() }
    single { AssertionManager(get()) }
    single { ClearsyCircuitParser() }
    single { CircuitViewModel(get(), get()) }
    single { AssertionsViewModel(get()) }
    single { ProjectViewModel(get()) }
    single { FailedAssertionViewModel() }
}
