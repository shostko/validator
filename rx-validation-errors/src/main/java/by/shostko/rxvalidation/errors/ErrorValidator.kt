@file:Suppress("unused")

package by.shostko.rxvalidation.errors

import by.shostko.errors.Error
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

    companion object {
        fun <T> predicate(code: ErrorCode?, function: (T) -> Boolean): Validator<T> = SimplePredicate(code, function)
        fun <T> predicate(function: (T) -> Boolean): Validator<T> = SimplePredicate(null, function)
        fun <T> action(code: ErrorCode?, function: (T) -> Unit): Validator<T> = SimpleAction(code, function)
        fun <T> action(function: (T) -> Unit): Validator<T> = SimpleAction(null, function)
        fun <T> simple(function: (T) -> ErrorCode?): Validator<T> = SimpleCode(function)
    }
}

private class SimplePredicate<T>(code: ErrorCode?, private val function: (T) -> Boolean) : ErrorValidator.Predicate<T>(code) {
    constructor(function: (T) -> Boolean) : this(null, function)

    override fun isValid(value: T): Boolean = function(value)
}

private class SimpleAction<T>(private val code: ErrorCode?, private val function: (T) -> Unit) : Validator<T>() {
    override fun validate(value: T) {
        try {
            function(value)
        } catch (th: Throwable) {
            if (th is Error && code == null) {
                throw th
            } else {
                throw Error.wrap(th, code ?: BaseValidationErrorCode(this, null))
            }
        }
    }
}

private class SimpleCode<T>(private val function: (T) -> ErrorCode?) : Validator<T>() {
    override fun validate(value: T) {
        try {
            val result = function(value)
            if (result != null) {
                throw ValidationError(result)
            }
        } catch (th: Throwable) {
            if (th is Error) {
                throw th
            } else {
                throw Error.wrap(th, BaseValidationErrorCode(this, null))
            }
        }
    }
}

fun <T> Validator.Companion.predicate(code: ErrorCode?, function: (T) -> Boolean): Validator<T> = SimplePredicate(code, function)