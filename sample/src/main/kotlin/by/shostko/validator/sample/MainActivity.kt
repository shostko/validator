package by.shostko.validator.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.shostko.validator.Validation
import by.shostko.validator.ValidationResult
import by.shostko.validator.sample.ui.theme.SampleTheme
import by.shostko.validator.strings.ValidationException

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.factory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleValidatorScreen(viewModel)
        }
    }
}

@Composable
private fun SampleValidatorScreen(viewModel: MainViewModel) {
    SampleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SampleTextField(
                    label = "Email",
                    validation = viewModel.email,
                )
                SampleTextField(
                    label = "Username",
                    validation = viewModel.username,
                )
                SampleTextField(
                    label = "Password",
                    validation = viewModel.password,
                )
                val isSubmitEnabled by viewModel.submit.collectAsState(false)
                Button(
                    onClick = { viewModel.onSubmitClick() },
                    enabled = isSubmitEnabled,
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SampleTextField(
    label: String,
    validation: Validation<String, String>,
    modifier: Modifier = Modifier,
) {
    val value by validation.valueFlow.collectAsState("")
    val result by validation.resultFlow.collectAsState(ValidationResult.valid(""))
    OutlinedTextField(
        value = value,
        onValueChange = validation::onNewValue,
        label = { Text(label) },
        supportingText = { ErrorText(result) },
        isError = !result.isValid,
        modifier = modifier,
        singleLine = true,
    )
}

@Composable
private fun ErrorText(result: ValidationResult<*, *>) {
    if (result is ValidationResult.Invalid) {
        val text = when (val reason = result.reason) {
            is String -> reason
            is Int -> stringResource(reason)
            is ValidationException -> reason.reason
            is Throwable -> reason.message
            else -> null
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text ?: "Unknown error!",
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFF000000)
private fun SampleValidatorScreenPreview() {
    SampleValidatorScreen(MainViewModel(MainRepository()))
}
