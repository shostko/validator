@file:Suppress("unused")

package by.shostko.validator.strings

class NotEmptyValidator<T : CharSequence>(
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.isEmpty()) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value should not be empty")
        }
    }
}

class EmptyValidator<T : CharSequence>(
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.isNotEmpty()) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value should be empty")
        }
    }
}

class NotBlankValidator<T : CharSequence>(
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.isBlank()) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value should not be blank")
        }
    }
}

class BlankValidator<T : CharSequence>(
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.isNotBlank()) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value should be blank")
        }
    }
}

class LengthValidator<T : CharSequence>(
    private val expectedLength: Int,
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.length != expectedLength) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value length should be $expectedLength")
        }
    }
}

class LengthLessValidator<T : CharSequence>(
    private val limit: Int,
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.length >= limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value length should be less than $limit")
        }
    }
}

class LengthLessOrEqualValidator<T : CharSequence>(
    private val limit: Int,
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.length > limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value length should be less or equal than $limit")
        }
    }
}

class LengthOverValidator<T : CharSequence>(
    private val limit: Int,
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.length <= limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value length should be over than $limit")
        }
    }
}

class LengthOverOrEqualValidator<T : CharSequence>(
    private val limit: Int,
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.length < limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value length should be over or equal than $limit")
        }
    }
}

class SingleCharValidator<T : CharSequence>(
    private val tag: String? = null,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {
    override fun validate(value: T) {
        if (value.length != 1) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value should contain only one char")
        }
    }
}
