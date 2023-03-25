package by.shostko.validator.exception

import by.shostko.validator.DelegatedValidator
import by.shostko.validator.ValidationResult

class ValidationException internal constructor(
    tag: String?,
    reason: String?,
    cause: Throwable? = null,
) : RuntimeException(
    message = if (tag.isNullOrBlank()) {
        reason
    } else {
        "$tag: $reason"
    },
    cause = cause,
)

abstract class BaseValidator<T : Any?> internal constructor(
    private val tag: String? = null,
) : DelegatedValidator<Any, T, ValidationException>() {

    override suspend fun invoke(value: T): ValidationResult<T, ValidationException> =
        try {
            validate(value)
            ValidationResult.valid(value)
        } catch (e: ValidationException) {
            ValidationResult.invalid(value, e)
        } catch (th: Throwable) {
            ValidationResult.invalid(value, ValidationException(tag, th.message, th))
        }

    internal abstract fun validate(value: T)
}
