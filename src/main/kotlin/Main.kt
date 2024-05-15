import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.core.model.examples.exampleWithShortCircuit
import org.example.csp_generator.generator.CspGenerator
import org.example.verifier.fdr.Verifier
import org.example.verifier.model.AssertionType
import java.nio.file.Paths

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    main2()
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