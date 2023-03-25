@file:Suppress("unused")

package by.shostko.validator.exception

class RangeValidator<T : Comparable<T>>(
    private val start: T,
    private val end: T,
    private val startInclusive: Boolean = true,
    private val endInclusive: Boolean = true,
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {

    override fun validate(value: T) {
        if ((startInclusive && value < start) || (!startInclusive && value <= start)
            || (endInclusive && value > end) || (!endInclusive && value >= end)
        ) {
            throw ValidationException(tag, reason?.invoke(value) ?: "The value is not in the required range ${range()}")
        }
    }

    private fun range(): CharSequence = StringBuilder()
        .append(if (startInclusive) '[' else '(')
        .append(start)
        .append(',')
        .append(end)
        .append(if (endInclusive) ']' else ')')
}

class LessValidator<T : Comparable<T>>(
    private val limit: T,
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {

    override fun validate(value: T) {
        if (value >= limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value length should be less than $limit")
        }
    }
}

class LessOrEqualValidator<T : Comparable<T>>(
    private val limit: T,
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {

    override fun validate(value: T) {
        if (value > limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value length should be less or equal than $limit")
        }
    }
}

class OverValidator<T : Comparable<T>>(
    private val limit: T,
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {

    override fun validate(value: T) {
        if (value <= limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value length should be over than $limit")
        }
    }
}

class OverLessOrEqualValidator<T : Comparable<T>>(
    private val limit: T,
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {

    override fun validate(value: T) {
        if (value < limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value length should be over or equal than $limit")
        }
    }
}
