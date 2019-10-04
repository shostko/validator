@file:Suppress("unused")

package by.shostko.rxvalidation

private class BoolValidationException(message: String? = null) : ValidationException(message)

class PositiveValidator(private val message: String? = null) : Validator<Boolean>() {
    override fun validate(value: Boolean) {
        if (!value) {
            throw BoolValidationException(message ?: "Value should be true")
        }
    }
}

class NegativeValidator(private val message: String? = null) : Validator<Boolean>() {
    override fun validate(value: Boolean) {
        if (value) {
            throw BoolValidationException(message ?: "Value should be false")
        }
    }
}

class AlwaysSuccessValidator<T> : Validator<T>() {
    override fun validate(value: T) {}
}

class AlwaysFailValidator<T> : Validator<T>() {
    override fun validate(value: T) {
        throw BoolValidationException("This validation always fails")
    }
}