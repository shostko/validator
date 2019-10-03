@file:Suppress("unused")

package by.shostko.rxvalidation.errors

import by.shostko.errors.Error
import by.shostko.errors.NoError
import by.shostko.rxvalidation.AbsValidation
import by.shostko.rxvalidation.ValidationResult

fun ValidationResult.error() = when (this) {
    ValidationResult.Valid -> NoError
    is ValidationResult.Invalid -> Error.cast(throwable ?: Throwable())
}

fun AbsValidation<*>.error() = result.map(ValidationResult::error)