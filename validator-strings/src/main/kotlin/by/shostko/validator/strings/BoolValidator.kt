@file:Suppress("unused")

package by.shostko.validator.strings

class PositiveValidator(
    private val tag: String? = null,
    private val reason: (() -> String?)? = null,
) : BaseValidator<Boolean>(tag) {
    override fun validate(value: Boolean) {
        if (!value) {
            throw ValidationException(tag, reason?.invoke() ?: "Value should be true")
        }
    }
}

class NegativeValidator(
    private val tag: String? = null,
    private val reason: (() -> String?)? = null,
) : BaseValidator<Boolean>(tag) {
    override fun validate(value: Boolean) {
        if (value) {
            throw ValidationException(tag, reason?.invoke() ?: "Value should be false")
        }
    }
}
