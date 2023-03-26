@file:Suppress("unused")

package by.shostko.validator.android

class RangeValidator<T : Comparable<T>>(
    private val start: T,
    private val end: T,
    private val startInclusive: Boolean = true,
    private val endInclusive: Boolean = true,
    private val tag: String? = null,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {

    override fun validate(value: T) {
        if ((startInclusive && value < start) || (!startInclusive && value <= start)
            || (endInclusive && value > end) || (!endInclusive && value >= end)
        ) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_range)
        }
    }
}

class LessValidator<T : Comparable<T>>(
    private val limit: T,
    private val tag: String? = null,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {

    override fun validate(value: T) {
        if (value >= limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_less)
        }
    }
}

class LessOrEqualValidator<T : Comparable<T>>(
    private val limit: T,
    private val tag: String? = null,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {

    override fun validate(value: T) {
        if (value > limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_less)
        }
    }
}

class OverValidator<T : Comparable<T>>(
    private val limit: T,
    private val tag: String? = null,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {

    override fun validate(value: T) {
        if (value <= limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_over)
        }
    }
}

class OverLessOrEqualValidator<T : Comparable<T>>(
    private val limit: T,
    private val tag: String? = null,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {

    override fun validate(value: T) {
        if (value < limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_over)
        }
    }
}
