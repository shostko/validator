@file:Suppress("unused")

package by.shostko.rxvalidation

import android.text.TextUtils
import by.shostko.errors.ErrorCode
import by.shostko.rxvalidation.errors.BaseValidationErrorCode
import by.shostko.rxvalidation.errors.ValidationError

private class StringValidationException(code: ErrorCode) : ValidationError(code)

class NotEmptyValidator(private val code: ErrorCode? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.isEmpty()) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should not be empty"))
        }
    }
}

class EmptyValidator(private val code: ErrorCode? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.isNotEmpty()) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should be empty"))
        }
    }
}

class NotBlankValidator(private val code: ErrorCode? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.isBlank()) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should not be blank"))
        }
    }
}

class BlankValidator(private val code: ErrorCode? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.isNotBlank()) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should be blank"))
        }
    }
}

class LengthValidator(private val expectedLength: Int, private val code: ErrorCode? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.length != expectedLength) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value length should be $expectedLength"))
        }
    }
}

class LengthLessValidator(private val limit: Int, private val code: ErrorCode? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.length >= limit) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value length should be less than $limit"))
        }
    }
}

class LengthLessOrEqualValidator(private val limit: Int, private val code: ErrorCode? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.length > limit) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value length should be less or equal than $limit"))
        }
    }
}

class LengthOverValidator(private val limit: Int, private val code: ErrorCode? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.length <= limit) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value length should be over than $limit"))
        }
    }
}

class LengthOverOrEqualValidator(private val limit: Int, private val code: ErrorCode? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.length < limit) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value length should be over or equal than $limit"))
        }
    }
}

class SingleCharValidator(private val code: ErrorCode? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.length != 1) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should contain only one char"))
        }
    }
}

class NumericValidator(private val code: ErrorCode? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (!TextUtils.isDigitsOnly(value)) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should contain only digits"))
        }
    }
}

class GraphicValidator(private val code: ErrorCode? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (!TextUtils.isGraphic(value)) {
            throw StringValidationException(code ?: BaseValidationErrorCode(this, "Value should contain at least one printable char"))
        }
    }
}