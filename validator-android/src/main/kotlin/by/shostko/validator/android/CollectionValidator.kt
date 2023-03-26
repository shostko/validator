@file:Suppress("unused")

package by.shostko.validator.android

class NotEmptyCollectionValidator<T>(
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> Int?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.isEmpty()) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_not_empty_collection)
        }
    }
}

class EmptyCollectionValidator<T>(
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> Int?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.isNotEmpty()) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_empty_collection)
        }
    }
}

class SizeValidator<T>(
    private val expectedSize: Int,
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> Int?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.size != expectedSize) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_size)
        }
    }
}

class SizeLessValidator<T>(
    private val limit: Int,
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> Int?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.size >= limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_size_less)
        }
    }
}

class SizeLessOrEqualValidator<T>(
    private val limit: Int,
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> Int?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.size > limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_size_less)
        }
    }
}

class SizeOverValidator<T>(
    private val limit: Int,
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> Int?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.size <= limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_size_over)
        }
    }
}

class SizeOverOrEqualValidator<T>(
    private val limit: Int,
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> Int?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.size < limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_size_over)
        }
    }
}

class SingleItemValidator<T>(
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> Int?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.size != 1) {
            throw ValidationException(tag, reason?.invoke(value) ?: R.string.by_shostko_validator_single_item)
        }
    }
}