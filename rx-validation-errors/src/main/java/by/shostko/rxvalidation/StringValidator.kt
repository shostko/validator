@file:Suppress("unused")

package by.shostko.rxvalidation

import android.text.TextUtils
import by.shostko.errors.ErrorCode
import by.shostko.rxvalidation.errors.BaseValidationErrorCode
import by.shostko.rxvalidation.errors.ValidationError

private class StringValidationException(code: ErrorCode) : ValidationError(code)

class NotEmptyValidator<T : CharSequence>(private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.isEmpty()) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should not be empty"))
        }
    }
}

class EmptyValidator<T : CharSequence>(private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.isNotEmpty()) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should be empty"))
        }
    }
}

class NotBlankValidator<T : CharSequence>(private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.isBlank()) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should not be blank"))
        }
    }
}

class BlankValidator<T : CharSequence>(private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.isNotBlank()) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should be blank"))
        }
    }
}

class LengthValidator<T : CharSequence>(private val expectedLength: Int, private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.length != expectedLength) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value length should be $expectedLength"))
        }
    }
}

class LengthLessValidator<T : CharSequence>(private val limit: Int, private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.length >= limit) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value length should be less than $limit"))
        }
    }
}

class LengthLessOrEqualValidator<T : CharSequence>(private val limit: Int, private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.length > limit) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value length should be less or equal than $limit"))
        }
    }
}

class LengthOverValidator<T : CharSequence>(private val limit: Int, private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.length <= limit) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value length should be over than $limit"))
        }
    }
}

class LengthOverOrEqualValidator<T : CharSequence>(private val limit: Int, private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.length < limit) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value length should be over or equal than $limit"))
        }
    }
}

class SingleCharValidator<T : CharSequence>(private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.length != 1) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should contain only one char"))
        }
    }
}

class NumericValidator<T : CharSequence>(private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (!TextUtils.isDigitsOnly(value)) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should contain only digits"))
        }
    }
}

class GraphicValidator<T : CharSequence>(private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (!TextUtils.isGraphic(value)) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should contain at least one printable char"))
        }
    }
}