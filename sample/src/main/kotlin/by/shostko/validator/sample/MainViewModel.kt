package by.shostko.validator.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import by.shostko.validator.*
import by.shostko.validator.strings.EmailValidator
import by.shostko.validator.strings.LengthLessOrEqualValidator
import by.shostko.validator.strings.LengthOverOrEqualValidator
import by.shostko.validator.strings.NotEmptyValidator
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository,
) : ViewModel() {

    companion object {
        fun factory() = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(
                repository = MainRepository(),
            ) as T
        }
    }

    val email by EmailValidator<String>("Email") { "Email format is not correct" }

    val username by validators(
        NotEmptyValidator("Username.Empty") { "Username must not be empty!" },
        LengthOverOrEqualValidator(3, "Username.Length") { "Username must be at least 3 characters!" },
        Validator.custom<String, String>(
            reason = { it.message ?: "Error checking username on server" },
            block = { value ->
                if (repository.checkIfUsernameAvailable(value)) {
                    ValidationResult.valid(value)
                } else {
                    ValidationResult.invalid(value, "Username already taken")
                }
            },
        ),
    )

    val password by LengthOverOrEqualValidator<String>(6, "Password") { "Password must be at least 6 characters!" }

    val submit = combine(
        email.resultFlow.map { it.isValid },
        username.resultFlow.map { it.isValid },
        password.resultFlow.map { it.isValid },
    ) { emailIsValid, usernameIsValid, passwordIsValid ->
        emailIsValid && usernameIsValid && passwordIsValid
    }

    fun onSubmitClick() {
        viewModelScope.launch {
            repository.login(
                email = email.value,
                username = username.value,
                password = password.value,
            )
        }
    }
}
