@file:Suppress("unused")

package by.shostko.rxvalidation

import android.text.TextUtils

private class StringValidationException(message: String? = null) : ValidationException(message)

class NotEmptyValidator(private val message: String? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.isEmpty()) {
            throw StringValidationException(message ?: "Value should not be empty")
        }
    }
}

class EmptyValidator(private val message: String? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.isNotEmpty()) {
            throw StringValidationException(message ?: "Value should be empty")
        }
    }
}

class NotBlankValidator(private val message: String? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.isBlank()) {
            throw StringValidationException(message ?: "Value should not be blank")
        }
    }
}

class BlankValidator(private val message: String? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.isNotBlank()) {
            throw StringValidationException(message ?: "Value should be blank")
        }
    }
}

class LengthValidator(private val expectedLength: Int, private val message: String? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.length != expectedLength) {
            throw StringValidationException(message ?: "Value length should be $expectedLength")
        }
    }
}

class LengthLessValidator(private val limit: Int, private val message: String? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.length >= limit) {
            throw StringValidationException(message ?: "Value length should be less than $limit")
        }
    }
}

class LengthLessOrEqualValidator(private val limit: Int, private val message: String? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.length > limit) {
            throw StringValidationException(message ?: "Value length should be less or equal than $limit")
        }
    }
}

class LengthOverValidator(private val limit: Int, private val message: String? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.length <= limit) {
            throw StringValidationException(message ?: "Value length should be over than $limit")
        }
    }
}

class LengthOverOrEqualValidator(private val limit: Int, private val message: String? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.length < limit) {
            throw StringValidationException(message ?: "Value length should be over or equal than $limit")
        }
    }
}

class SingleCharValidator(private val message: String? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (value.length != 1) {
            throw StringValidationException(message ?: "Value should contain only one char")
        }
    }
}

class NumericValidator(private val message: String? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (!TextUtils.isDigitsOnly(value)) {
            throw StringValidationException(message ?: "Value should contain only digits")
        }
    }
}

class GraphicValidator(private val message: String? = null) : Validator<CharSequence>() {
    override fun validate(value: CharSequence) {
        if (!TextUtils.isGraphic(value)) {
            throw StringValidationException(message ?: "Value should contain at least one printable char")
        }
    }
}