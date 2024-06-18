import androidx.compose.ui.window.application
import core.model.Circuit
import csp_generator.generator.CspGenerator
import kotlinx.coroutines.runBlocking
import org.koin.core.context.GlobalContext.startKoin
import ui.App
import ui.di.appModule
import verifier.AssertionManager
import verifier.model.common.AssertionType


fun main() = application {
    startKoin {
        modules(appModule())
    }

    System.loadLibrary("libfdr_java")

    App()
}

//fun main(): Unit = runBlocking {
//    AssertionManager(CspGenerator()).runAssertionsReturnFailing(Circuit.DEFAULT, AssertionType.entries)
//}