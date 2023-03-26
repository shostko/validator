package by.shostko.validator

private class CustomValidator<THIS : Any, T : Any?, R : Any?>(
    private val block: suspend (T) -> ValidationResult<T, R>,
) : DelegatedValidator<THIS, T, R>() {
    override suspend fun invoke(value: T): ValidationResult<T, R> = block(value)
}

private class SafeValidator<THIS : Any, T : Any?, R : Any?>(
    private val reason: suspend (Throwable) -> R,
    private val block: suspend (T) -> ValidationResult<T, R>,
) : DelegatedValidator<THIS, T, R>() {
    override suspend fun invoke(value: T): ValidationResult<T, R> = try {
        block(value)
    } catch (th: Throwable) {
        ValidationResult.invalid(value, reason(th))
    }
}

fun <T : Any?, R : Any?> Validator.Companion.custom(
    block: suspend (T) -> ValidationResult<T, R>,
): DelegatedValidator<Any, T, R> = CustomValidator(block)

fun <T : Any?, R : Any?> Validator.Companion.custom(
    reason: suspend (Throwable) -> R,
    block: suspend (T) -> ValidationResult<T, R>,
): DelegatedValidator<Any, T, R> = SafeValidator(reason, block)

fun <T : Any?, R : Any?> Validator.Companion.predicate(
    reason: suspend (Throwable?) -> R,
    predicate: suspend (T) -> Boolean,
): DelegatedValidator<Any, T, R> = SafeValidator(reason) { value ->
    if (predicate(value)) {
        ValidationResult.valid(value)
    } else {
        ValidationResult.invalid(value, reason(null))
    }
}

fun <T : Any?, R : Any?> Validator.Companion.action(
    reason: suspend (Throwable?) -> R,
    action: suspend (T) -> Unit,
): DelegatedValidator<Any, T, R> = SafeValidator(reason) { value ->
    action(value)
    ValidationResult.valid(value)
}
