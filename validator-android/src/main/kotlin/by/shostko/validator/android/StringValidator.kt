@file:Suppress("unused")

package by.shostko.validator.android

class NotEmptyValidator<T : CharSequence>(
    private val tag: String? = null,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.isEmpty()) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_not_empty)
        }
    }
}

class EmptyValidator<T : CharSequence>(
    private val tag: String? = null,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.isNotEmpty()) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_empty)
        }
    }
}

class NotBlankValidator<T : CharSequence>(
    private val tag: String? = null,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.isBlank()) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_not_blank)
        }
    }
}

class BlankValidator<T : CharSequence>(
    private val tag: String? = null,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.isNotBlank()) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_blank)
        }
    }
}

class LengthValidator<T : CharSequence>(
    private val tag: String? = null,
    private val expectedLength: Int,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.length != expectedLength) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_length)
        }
    }
}

class LengthLessValidator<T : CharSequence>(
    private val tag: String? = null,
    private val limit: Int,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.length >= limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_length_less)
        }
    }
}

class LengthLessOrEqualValidator<T : CharSequence>(
    private val tag: String? = null,
    private val limit: Int,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.length > limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_length_less)
        }
    }
}

class LengthOverValidator<T : CharSequence>(
    private val tag: String? = null,
    private val limit: Int,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.length <= limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_length_over)
        }
    }
}

class LengthOverOrEqualValidator<T : CharSequence>(
    private val tag: String? = null,
    private val limit: Int,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.length < limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_length_over)
        }
    }
}

class SingleCharValidator<T : CharSequence>(
    private val tag: String? = null,
    private val reason: ((T) -> Int?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.length != 1) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_single_char)
        }
    }
}
