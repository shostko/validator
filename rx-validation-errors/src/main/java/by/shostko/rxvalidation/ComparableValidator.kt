@file:Suppress("unused")

package by.shostko.rxvalidation

import by.shostko.errors.ErrorCode
import by.shostko.rxvalidation.errors.BaseValidationErrorCode
import by.shostko.rxvalidation.errors.ValidationError

private class ComparableValidationException(code: ErrorCode) : ValidationError(code)

class RangeValidator<T : Comparable<T>>(
        private val start: T,
        private val end: T,
        private val startInclusive: Boolean = true,
        private val endInclusive: Boolean = true,
        private val code: ErrorCode? = null
) : Validator<T>() {
    constructor(start: T, end: T, code: ErrorCode? = null) : this(start, end, true, true, code)

    override fun validate(value: T) {
        if ((startInclusive && value < start) || (!startInclusive && value <= start)
                || (endInclusive && value > end) || (!endInclusive && value >= end)) {
            throw ComparableValidationException(code ?: BaseValidationErrorCode(this, "The value is not in the required range ${range()}"))
        }
    }

    private fun range(): CharSequence = StringBuilder()
            .append(if (startInclusive) '[' else '(')
            .append(start)
            .append(',')
            .append(end)
            .append(if (endInclusive) ']' else ')')
}

class LessValidator<T : Comparable<T>>(private val limit: T, private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value >= limit) {
            throw ComparableValidationException(code ?: BaseValidationErrorCode(this, "Value length should be less than $limit"))
        }
    }
}

class LessOrEqualValidator<T : Comparable<T>>(private val limit: T, private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value > limit) {
            throw ComparableValidationException(code ?: BaseValidationErrorCode(this, "Value length should be less or equal than $limit"))
        }
    }
}

class OverValidator<T : Comparable<T>>(private val limit: T, private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value <= limit) {
            throw ComparableValidationException(code ?: BaseValidationErrorCode(this, "Value length should be over than $limit"))
        }
    }
}

class OverLessOrEqualValidator<T : Comparable<T>>(private val limit: T, private val code: ErrorCode? = null) : Validator<T>() {
    override fun validate(value: T) {
        if (value < limit) {
            throw ComparableValidationException(code ?: BaseValidationErrorCode(this, "Value length should be over or equal than $limit"))
        }
    }
}