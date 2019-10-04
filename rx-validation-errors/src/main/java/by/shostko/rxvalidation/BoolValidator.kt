@file:Suppress("unused")

package by.shostko.rxvalidation

import by.shostko.errors.ErrorCode
import by.shostko.rxvalidation.errors.BaseValidationErrorCode
import by.shostko.rxvalidation.errors.ValidationError

private class BoolValidationException(code: ErrorCode) : ValidationError(code)

class PositiveValidator(private val code: ErrorCode? = null) : Validator<Boolean>() {
    override fun validate(value: Boolean) {
        if (!value) {
            throw BoolValidationException(code ?: BaseValidationErrorCode(this, "Value should be true"))
        }
    }
}

class NegativeValidator(private val code: ErrorCode? = null) : Validator<Boolean>() {
    override fun validate(value: Boolean) {
        if (value) {
            throw BoolValidationException(code ?: BaseValidationErrorCode(this, "Value should be false"))
        }
    }
}

class AlwaysSuccessValidator<T> : Validator<T>() {
    override fun validate(value: T) {}
}

class AlwaysFailValidator<T> : Validator<T>() {
    override fun validate(value: T) {
        throw BoolValidationException(BaseValidationErrorCode(this, "This validation always fails"))
    }
}