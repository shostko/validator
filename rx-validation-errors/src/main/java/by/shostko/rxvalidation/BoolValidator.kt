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

object AlwaysSuccessValidator : Validator<Boolean>() {
    override fun validate(value: Boolean) {}
}

object AlwaysFailValidator : Validator<Boolean>() {
    override fun validate(value: Boolean) {
        throw BoolValidationException(BaseValidationErrorCode(AlwaysFailValidator, "This validation always fails"))
    }
}