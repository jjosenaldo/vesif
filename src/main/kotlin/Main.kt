import androidx.compose.ui.window.application
import org.koin.core.context.GlobalContext.startKoin
import ui.App
import ui.di.appModule

fun main() = application {
    startKoin {
        modules(appModule())
    }

    App()
}

//fun main(): Unit = runBlocking {
//    AssertionManager(CspGenerator()).runAssertionsReturnFailing(Circuit.DEFAULT, AssertionType.entries)
//}