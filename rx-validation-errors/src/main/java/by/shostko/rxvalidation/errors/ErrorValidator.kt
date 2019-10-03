@file:Suppress("unused")

package by.shostko.rxvalidation.errors

import by.shostko.errors.EmptyErrorCode
import by.shostko.errors.ErrorCode
import by.shostko.rxvalidation.Validator

abstract class ErrorValidator<T> : Validator<T>() {
    final override fun validate(value: T) {
        val code = validate2(value)
        if (code !== EmptyErrorCode) {
            throw ValidationError(code)
        }
    }

    protected abstract fun validate2(value: T): ErrorCode
}