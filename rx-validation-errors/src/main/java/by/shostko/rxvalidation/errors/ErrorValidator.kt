@file:Suppress("unused")

package by.shostko.rxvalidation.errors

import by.shostko.errors.ErrorCode
import by.shostko.rxvalidation.Validator

abstract class ErrorValidator<T> : Validator<T>() {

    abstract class Predicate<T>(private val code: ErrorCode? = null) : Validator<T>() {
        final override fun validate(value: T) {
            if (!isValid(value)) {
                throw ValidationError(code ?: BaseValidationErrorCode(this, null))
            }
        }

        protected abstract fun isValid(value: T): Boolean
    }
}

private class SimplePredicate<T>(code: ErrorCode?, private val function: (T) -> Boolean) : ErrorValidator.Predicate<T>(code) {
    constructor(function: (T) -> Boolean) : this(null, function)

    override fun isValid(value: T): Boolean = function(value)
}

fun <T> Validator.Companion.predicate(code: ErrorCode?, function: (T) -> Boolean): Validator<T> = SimplePredicate(code, function)