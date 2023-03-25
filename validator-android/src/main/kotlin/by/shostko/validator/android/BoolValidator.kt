@file:Suppress("unused")

package by.shostko.validator.android

class PositiveValidator(
    private val tag: String? = null,
    private val reason: (() -> Int?)? = null,
) : BaseValidator<Boolean>(tag) {
    override fun validate(value: Boolean) {
        if (!value) {
            throw ValidationException(tag, reason?.invoke() ?: R.string.by_shostko_validator_positive)
        }
    }
}

class NegativeValidator(
    private val tag: String? = null,
    private val reason: (() -> Int?)? = null,
) : BaseValidator<Boolean>(tag) {
    override fun validate(value: Boolean) {
        if (value) {
            throw ValidationException(tag, reason?.invoke() ?: R.string.by_shostko_validator_negative)
        }
    }
}
