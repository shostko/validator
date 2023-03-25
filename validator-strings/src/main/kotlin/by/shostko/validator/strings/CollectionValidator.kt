@file:Suppress("unused")

package by.shostko.validator.strings

class NotEmptyCollectionValidator<T>(
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> String?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.isEmpty()) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Collection should not be empty")
        }
    }
}

class EmptyCollectionValidator<T>(
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> String?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.isNotEmpty()) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Collection should be empty")
        }
    }
}

class SizeValidator<T>(
    private val expectedSize: Int,
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> String?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.size != expectedSize) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Collection size should be $expectedSize")
        }
    }
}

class SizeLessValidator<T>(
    private val limit: Int,
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> String?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.size >= limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Collection size should be less than $limit")
        }
    }
}

class SizeLessOrEqualValidator<T>(
    private val limit: Int,
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> String?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.size > limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Collection size should be less or equal than $limit")
        }
    }
}

class SizeOverValidator<T>(
    private val limit: Int,
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> String?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.size <= limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Collection size should be over than $limit")
        }
    }
}

class SizeOverOrEqualValidator<T>(
    private val limit: Int,
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> String?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.size < limit) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Collection size should be over or equal than $limit")
        }
    }
}

class SingleItemValidator<T>(
    private val tag: String? = null,
    private val reason: ((Collection<T>) -> String?)? = null,
) : BaseValidator<Collection<T>>(tag) {
    override fun validate(value: Collection<T>) {
        if (value.size != 1) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Collection should contain only one item")
        }
    }
}