import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import org.example.core.model.examples.exampleWithShortCircuit
import org.example.csp_generator.generator.CspGenerator
import org.example.verifier.fdr.Verifier
import org.example.verifier.model.AssertionType
import presentation.XmlSelector
import java.nio.file.Paths

@Composable
@Preview
fun App() {
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }

    MaterialTheme {
        Column {
            XmlSelector(onFileSelected = {
                coroutineScope.launch {
                    text = it()
                }
            })
            if (text.isNotEmpty()) Text("file content: $text")
        }

    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

fun main2() {
    val circuit = exampleWithShortCircuit

    CspGenerator.generateCsp(
        Paths.get(
            System.getProperty("user.dir"), "output"
        ).toString(), circuit
    )

    val assertionsToCheck = AssertionType.entries
    val allFailingAssertions = Verifier.checkFailingAssertions(circuit, assertionsToCheck)

    assertionsToCheck.forEach {
        val failingAssertions = allFailingAssertions[it] ?: listOf()
        println("$it: ${if (failingAssertions.isEmpty()) "✓" else "⚠"}")
        failingAssertions.forEach { result -> println(result.details) }
        println()
    }
}