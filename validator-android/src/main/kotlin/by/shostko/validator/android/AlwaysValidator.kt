@file:Suppress("unused")

package by.shostko.validator.android

class AlwaysSuccessValidator<T> : BaseValidator<T>(null) {
    override fun validate(value: T) {
        // do nothing
    }
}

class AlwaysFailValidator<T>(
    private val tag: String? = null,
    private val reason: (() -> Int?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        throw ValidationException(tag, reason?.invoke() ?: R.string.by_shostko_validator_always_fail)
    }
}
