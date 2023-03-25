@file:Suppress("unused")

package by.shostko.validator.strings

class AlwaysSuccessValidator<T> : BaseValidator<T>(null) {
    override fun validate(value: T) {
        // do nothing
    }
}

class AlwaysFailValidator<T>(
    private val tag: String? = null,
    private val reason: (() -> String?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        throw ValidationException(tag, reason?.invoke() ?: "This validation always fails")
    }
}
