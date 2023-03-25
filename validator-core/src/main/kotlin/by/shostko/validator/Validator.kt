package by.shostko.validator

interface Validator<T : Any?, R : Any?> : suspend (T) -> ValidationResult<T, R>

private class CompositeValidator<T : Any?, R : Any?>(
    private vararg val validators: Validator<T, R>,
) : Validator<T, R> {

    override suspend fun invoke(value: T): ValidationResult<T, R> {
        validators.forEach { validator ->
            val result = validator.invoke(value)
            if (!result.isValid) {
                return result
            }
        }
        return ValidationResult.valid(value)
    }
}

private class MappedValidator<T : Any?, RIN : Any?, ROUT : Any?>(
    private val validator: Validator<T, RIN>,
    private val mapper: suspend (RIN) -> ROUT,
) : Validator<T, ROUT> {

    override suspend fun invoke(value: T): ValidationResult<T, ROUT> {
        val result = validator.invoke(value)
        return if (result is ValidationResult.Invalid) {
            val mapped = mapper.invoke(result.reason)
            ValidationResult.invalid(value, mapped)
        } else {
            ValidationResult.valid(value)
        }
    }
}

fun <T : Any?, R : Any?> validators(
    vararg validators: Validator<T, R>,
): Validator<T, R> = CompositeValidator(*validators)

fun <T : Any?, RIN : Any?, ROUT : Any?> Validator<T, RIN>.map(
    mapper: suspend (RIN) -> ROUT,
): Validator<T, ROUT> = MappedValidator(this, mapper)
