package ui.screens.main.sub_screens.assertions

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.sharp.Check
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ui.common.*
import ui.navigation.AppNavigator
import ui.screens.main.sub_screens.assertions.model.*
import ui.window.AppWindowManager
import ui.screens.main.sub_screens.assertions.model.AssertionData
import ui.screens.main.sub_screens.assertions.model.EmptyAssertionData
import ui.screens.main.sub_screens.assertions.model.MultiselectAssertionData
import verifier.model.common.AssertionType
import verifier.util.AssertionTimeoutException
import verifier.util.FdrNotFoundException
import verifier.util.InvalidFdrException

@Composable
fun AssertionsPane(
    viewModel: AssertionsViewModel = koinInject()
) {
    if (viewModel.assertions.isEmpty()) return

    ErrorView()

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)

    Pane {
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                Assertions(
                    when {
                        viewModel.assertions.any { it is AssertionInitial } -> viewModel.assertions
                        else -> viewModel.assertions.filter { it !is AssertionNotSelected }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            VerticalScrollbar(
                modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd),
                adapter = rememberScrollbarAdapter(scrollState)
            )
        }

        viewModel.assertions.let { assertions ->
            if (assertions.all { it is AssertionInitial }) {
                Divider(color = tertiaryBackgroundColor)
                AppButton(
                    enabled = assertions.any { (it as AssertionInitial).selected },
                    modifier = Modifier.padding(12.dp),
                    onClick = {
                        if (viewModel.assertions.none { it is AssertionRunning }) {
                            scope.launch {
                                viewModel.runSelectedAssertions()
                            }
                        }
                    }
                ) {
                    AppText(text = "Check properties")
                }
            }

        }

    }
}

@Composable
private fun ErrorView(
    viewModel: AssertionsViewModel = koinInject(),
    windowManager: AppWindowManager = koinInject()
) {
    viewModel.assertions.firstOrNull { it is AssertionError }?.let {
        ErrorDialog(
            when ((it as AssertionError).error) {
                is InvalidFdrException -> ErrorDialogConfig(
                    "Invalid FDR",
                    "Your FDR is not valid. Please provide a path to a valid FDR version in Settings or add it to your PATH.",
                    "Settings",
                    windowManager::openSettings,
                    viewModel::reset
                )

                is FdrNotFoundException -> ErrorDialogConfig(
                    "FDR not found",
                    "FDR not found. Please provide a path to a valid FDR version in Settings or add it to your PATH.",
                    "Settings",
                    windowManager::openSettings,
                    viewModel::reset
                )

                is AssertionTimeoutException -> ErrorDialogConfig(
                    "Timeout",
                    "The checking took too long to run. Either select fewer properties or choose a simpler circuit, or increase the timeout time in Settings.",
                    "Settings",
                    windowManager::openSettings,
                    viewModel::reset
                )

                else -> ErrorDialogConfig(
                    "Error",
                    "An error has occurred: ${it.error.message}",
                    onDialogClosed = viewModel::reset
                )
            }
        )
    }
}


@Composable
fun Assertions(assertions: List<AssertionState>) {
    assertions.mapIndexed { index, assertion ->
        if (index > 0) Divider(
            color = tertiaryBackgroundColor,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
        )
        AssertionView(assertion)
    }
}

@Composable
fun AssertionView(
    assertionState: AssertionState,
    viewModel: AssertionsViewModel = koinInject(),
    navigator: AppNavigator = koinInject()
) {
    val verticalAlignment = Alignment.CenterVertically

    if (assertionState is AssertionInitial)
        Column {
            Row(verticalAlignment = verticalAlignment,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { viewModel.setSelected(!assertionState.selected, assertionState) }
            ) {
                Checkbox(
                    checked = assertionState.selected,
                    onCheckedChange = { viewModel.setSelected(it, assertionState) },
                    modifier = Modifier.padding(16.dp).width(16.dp).height(16.dp)
                )
                AppText(text = assertionState.name)
            }

            AssertionDataView(assertionState.type, assertionState.data, assertionState.selected)
        }
    else Row(verticalAlignment = Alignment.CenterVertically) {
        if (assertionState !is AssertionError) {
            val modifier =
                Modifier.padding(top = 8.dp, bottom = 8.dp, end = 8.dp, start = 8.dp).width(24.dp).height(24.dp)

            when (assertionState) {
                is AssertionFailed -> Icon(
                    Icons.Sharp.Close,
                    "Error icon",
                    tint = Color(0xfff72702),
                    modifier = modifier
                )

                is AssertionPassed -> Icon(
                    Icons.Sharp.Check,
                    "Check icon",
                    tint = Color(0xff5fe322),
                    modifier = modifier
                )

                is AssertionRunning -> CircularProgressIndicator(strokeWidth = 2.dp, modifier = modifier)
                else -> {}
            }
        }

        AppText(text = assertionState.name)

        if (assertionState is AssertionFailed && assertionState.results.isNotEmpty())
            AppIconButton(
                imageVector = Icons.Default.Info,
                contentDescription = "Details",
                tint = iconColor,
                modifier = Modifier.align(Alignment.Top).padding(top = 2.dp, start = 2.dp),
                size = 20.dp
            ) {
                navigator.navToFailedAssertions(assertionState.results)
            }
    }
}

@Composable
private fun AssertionDataView(
    assertionType: AssertionType,
    assertionData: AssertionData,
    selected: Boolean,
    viewModel: AssertionsViewModel = koinInject(),
) {
    when (assertionData) {
        is EmptyAssertionData -> {}
        is MultiselectAssertionData<*, *> -> {
            if (!selected) return

            key(viewModel.multiselectDataId[assertionType]) {
                MultiselectAssertionDataView(
                    assertionData,
                    onNewData = {
                        viewModel.updateMultiselect(assertionType, it)
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}