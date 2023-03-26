package by.shostko.validator

sealed class ValidationResult<T : Any?, R : Any?> private constructor(
    open val value: T,
) {
    val isValid: Boolean
        get() = this is Valid<*>

    data class Valid<T : Any?>(
        override val value: T,
    ) : ValidationResult<T, Nothing>(value) {
        override fun toString(): String = "Valid($value)"
    }

    data class Invalid<T : Any?, R : Any?>(
        override val value: T,
        val reason: R,
    ) : ValidationResult<T, R>(value) {
        override fun toString(): String = if (reason == null) {
            "Invalid($value)"
        } else {
            "Invalid($value: $reason)"
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T : Any?, R : Any?> valid(value: T): ValidationResult<T, R> = Valid(value) as ValidationResult<T, R>
        fun <T : Any?, R : Any?> invalid(value: T, reason: R): ValidationResult<T, R> = Invalid(value, reason)
    }
}
