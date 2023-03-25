package by.shostko.validator.android

import androidx.annotation.StringRes
import by.shostko.validator.DelegatedValidator
import by.shostko.validator.ValidationResult

class ValidationException internal constructor(
    tag: String?,
    @StringRes internal val reason: Int,
    cause: Throwable? = null,
) : RuntimeException(
    message = "ResourceValidationException($tag: $reason)", // TODO extract name from resource
    cause = cause,
)

abstract class BaseValidator<T : Any?> internal constructor(
    private val tag: String? = null,
) : DelegatedValidator<Any, T, Int>() {

    override suspend fun invoke(value: T): ValidationResult<T, Int> =
        try {
            validate(value)
            ValidationResult.valid(value)
        } catch (e: ValidationException) {
            ValidationResult.invalid(value, e.reason)
        } catch (th: Throwable) {
            ValidationResult.invalid(value, 0)
        }

    internal abstract fun validate(value: T)
}
