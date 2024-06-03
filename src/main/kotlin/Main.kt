import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import csp_generator.generator.CspGenerator
import input.ClearsyCircuitParser
import input.ClearsyProjectParser
import verifier.AssertionManager
import org.koin.compose.KoinApplication
import org.koin.dsl.module
import presentation.AppContent
import presentation.screens.project_selected.assertions.AssertionsViewModel
import presentation.screens.project_selected.failed_assertion.FailedAssertionViewModel
import presentation.screens.project_selected.ProjectSelectedViewModel
import presentation.screens.project_selected.select_circuit.CircuitViewModel
import presentation.screens.select_project.ProjectViewModel

fun appModule() = module {
    single { CspGenerator() }
    single { ClearsyProjectParser() }
    single { AssertionManager(get()) }
    single { ClearsyCircuitParser() }
    single { CircuitViewModel(get(), get()) }
    single { AssertionsViewModel(get()) }
    single { ProjectViewModel(get()) }
    single { ProjectSelectedViewModel() }
    single { FailedAssertionViewModel() }
}

@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        MaterialTheme {
            AppContent()
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

//fun main() {
//    AssertionManager(CspGenerator()).exampleCheckAssertions()
//}