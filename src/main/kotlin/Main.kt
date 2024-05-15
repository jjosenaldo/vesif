import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import csp_generator.generator.CspGenerator
import input.CircuitInputManager
import org.example.verifier.fdr.AssertionManager
import org.koin.compose.KoinApplication
import org.koin.dsl.module
import presentation.AppContent
import presentation.assertions.AssertionsViewModel
import presentation.circuit.CircuitViewModel

fun appModule() = module {
    single { CspGenerator() }
    single { AssertionManager(get()) }
    single { CircuitInputManager() }
    single { CircuitViewModel(get(), get()) }
    single { AssertionsViewModel(get(), get()) }
}

@Composable
@Preview
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
