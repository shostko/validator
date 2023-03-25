package by.shostko.validator.strings

import by.shostko.validator.DelegatedValidator
import by.shostko.validator.ValidationResult

class ValidationException internal constructor(
    tag: String?,
    reason: String?,
    cause: Throwable? = null,
) : RuntimeException(
    if (tag.isNullOrBlank()) {
        reason
    } else {
        "$tag: $reason"
    },
    cause,
)

abstract class BaseValidator<T : Any?> internal constructor(
    private val tag: String? = null,
) : DelegatedValidator<Any, T, String>() {

    override suspend fun invoke(value: T): ValidationResult<T, String> =
        try {
            validate(value)
            ValidationResult.valid(value)
        } catch (th: Throwable) {
            ValidationResult.invalid(value, th.message ?: "")
        }

    internal abstract fun validate(value: T)
}
