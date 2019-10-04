@file:Suppress("unused")

package by.shostko.rxvalidation.errors

import by.shostko.errors.Error
import by.shostko.errors.ErrorCode
import by.shostko.errors.SimpleErrorCode
import by.shostko.rxvalidation.Validator

open class ValidationError(code: ErrorCode) : Error.Custom(code) {
    override fun toString(): String = "Validation${super.toString()}"
}

open class BaseValidationErrorCode(domain: Class<out Validator<*>>, text: String?) : SimpleErrorCode(domain, null, text) {
    constructor(validator: Validator<*>, text: String?) : this(validator.javaClass, text)
}