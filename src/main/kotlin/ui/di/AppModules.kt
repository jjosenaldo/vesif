package ui.di

import core.utils.preferences.Preferences
import csp_generator.generator.CspGenerator
import input.ClearsyCircuitParser
import input.ClearsyProjectParser
import org.koin.dsl.module
import ui.navigation.AppNavigator
import ui.screens.main.sub_screens.assertions.AssertionsViewModel
import ui.screens.main.sub_screens.failed_assertion.FailedAssertionViewModel
import ui.screens.main.sub_screens.select_circuit.CircuitViewModel
import ui.screens.select_project.ProjectViewModel
import ui.screens.settings.SettingsViewModel
import ui.window.AppWindowManager
import verifier.AssertionManager
import verifier.util.FdrLoader


fun appModule() = module {
    single { AppWindowManager() }
    single { AppNavigator() }
    single { CspGenerator() }
    single { ClearsyProjectParser() }
    single { AssertionManager(get()) }
    single { ClearsyCircuitParser() }
    single { Preferences }
    single { FdrLoader }
    single { SettingsViewModel(get(), get()) }
    single { CircuitViewModel(get()) }
    single { AssertionsViewModel(get()) }
    single { ProjectViewModel(get(), get()) }
    single { FailedAssertionViewModel() }
}
