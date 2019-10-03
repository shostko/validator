@file:Suppress("unused")

package by.shostko.rxvalidation

import by.shostko.errors.ErrorCode
import by.shostko.rxvalidation.errors.BaseValidationErrorCode
import by.shostko.rxvalidation.errors.ValidationError

private class CollectionValidationException(code: ErrorCode) : ValidationError(code)

class NotEmptyCollectionValidator<T>(private val code: ErrorCode? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.isEmpty()) {
            throw CollectionValidationException(code ?: BaseValidationErrorCode(this, "Collection should not be empty"))
        }
    }
}

class EmptyCollectionValidator<T>(private val code: ErrorCode? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.isNotEmpty()) {
            throw CollectionValidationException(code ?: BaseValidationErrorCode(this, "Collection should be empty"))
        }
    }
}

class SizeValidator<T>(private val expectedSize: Int, private val code: ErrorCode? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.size != expectedSize) {
            throw CollectionValidationException(code ?: BaseValidationErrorCode(this, "Collection size should be $expectedSize"))
        }
    }
}

class SizeLessValidator<T>(private val limit: Int, private val code: ErrorCode? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.size >= limit) {
            throw CollectionValidationException(code ?: BaseValidationErrorCode(this, "Collection size should be less than $limit"))
        }
    }
}

class SizeLessOrEqualValidator<T>(private val limit: Int, private val code: ErrorCode? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.size > limit) {
            throw CollectionValidationException(code ?: BaseValidationErrorCode(this, "Collection size should be less or equal than $limit"))
        }
    }
}

class SizeOverValidator<T>(private val limit: Int, private val code: ErrorCode? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.size <= limit) {
            throw CollectionValidationException(code ?: BaseValidationErrorCode(this, "Collection size should be over than $limit"))
        }
    }
}

class SizeOverOrEqualValidator<T>(private val limit: Int, private val code: ErrorCode? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.size < limit) {
            throw CollectionValidationException(code ?: BaseValidationErrorCode(this, "Collection size should be over or equal than $limit"))
        }
    }
}

class SingleItemValidator<T>(private val code: ErrorCode? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.size != 1) {
            throw CollectionValidationException(code ?: BaseValidationErrorCode(this, "Collection should contain only one item"))
        }
    }
}