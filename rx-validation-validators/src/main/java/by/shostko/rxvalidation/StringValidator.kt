@file:Suppress("unused")

package by.shostko.rxvalidation

import android.text.TextUtils

private class StringValidationException(message: String? = null) : ValidationException(message)

class NotEmptyValidator<T : CharSequence>(private val message: String? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.isEmpty()) {
            throw StringValidationException(message ?: "Value should not be empty")
        }
    }
}

class EmptyValidator<T : CharSequence>(private val message: String? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.isNotEmpty()) {
            throw StringValidationException(message ?: "Value should be empty")
        }
    }
}

class NotBlankValidator<T : CharSequence>(private val message: String? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.isBlank()) {
            throw StringValidationException(message ?: "Value should not be blank")
        }
    }
}

class BlankValidator<T : CharSequence>(private val message: String? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.isNotBlank()) {
            throw StringValidationException(message ?: "Value should be blank")
        }
    }
}

class LengthValidator<T : CharSequence>(private val expectedLength: Int, private val message: String? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.length != expectedLength) {
            throw StringValidationException(message ?: "Value length should be $expectedLength")
        }
    }
}

class LengthLessValidator<T : CharSequence>(private val limit: Int, private val message: String? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.length >= limit) {
            throw StringValidationException(message ?: "Value length should be less than $limit")
        }
    }
}

class LengthLessOrEqualValidator<T : CharSequence>(private val limit: Int, private val message: String? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.length > limit) {
            throw StringValidationException(message ?: "Value length should be less or equal than $limit")
        }
    }
}

class LengthOverValidator<T : CharSequence>(private val limit: Int, private val message: String? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.length <= limit) {
            throw StringValidationException(message ?: "Value length should be over than $limit")
        }
    }
}

class LengthOverOrEqualValidator<T : CharSequence>(private val limit: Int, private val message: String? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.length < limit) {
            throw StringValidationException(message ?: "Value length should be over or equal than $limit")
        }
    }
}

class SingleCharValidator<T : CharSequence>(private val message: String? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value.length != 1) {
            throw StringValidationException(message ?: "Value should contain only one char")
        }
    }
}

class NumericValidator<T : CharSequence>(private val message: String? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (!TextUtils.isDigitsOnly(value)) {
            throw StringValidationException(message ?: "Value should contain only digits")
        }
    }
}

class GraphicValidator<T : CharSequence>(private val message: String? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (!TextUtils.isGraphic(value)) {
            throw StringValidationException(message ?: "Value should contain at least one printable char")
        }
    }
}