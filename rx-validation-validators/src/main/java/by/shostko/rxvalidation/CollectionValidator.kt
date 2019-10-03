@file:Suppress("unused")

package by.shostko.rxvalidation

private class CollectionValidationException(message: String? = null) : ValidationException(message)

class NotEmptyCollectionValidator<T>(private val message: String? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.isEmpty()) {
            throw CollectionValidationException(
                message ?: "Collection should not be empty"
            )
        }
    }
}

class EmptyCollectionValidator<T>(private val message: String? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.isNotEmpty()) {
            throw CollectionValidationException(
                message ?: "Collection should be empty"
            )
        }
    }
}

class SizeValidator<T>(private val expectedSize: Int, private val message: String? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.size != expectedSize) {
            throw CollectionValidationException(
                message ?: "Collection size should be $expectedSize"
            )
        }
    }
}

class SizeLessValidator<T>(private val limit: Int, private val message: String? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.size >= limit) {
            throw CollectionValidationException(
                message ?: "Collection size should be less than $limit"
            )
        }
    }
}

class SizeLessOrEqualValidator<T>(private val limit: Int, private val message: String? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.size > limit) {
            throw CollectionValidationException(
                message ?: "Collection size should be less or equal than $limit"
            )
        }
    }
}

class SizeOverValidator<T>(private val limit: Int, private val message: String? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.size <= limit) {
            throw CollectionValidationException(
                message ?: "Collection size should be over than $limit"
            )
        }
    }
}

class SizeOverOrEqualValidator<T>(private val limit: Int, private val message: String? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.size < limit) {
            throw CollectionValidationException(
                message ?: "Collection size should be over or equal than $limit"
            )
        }
    }
}

class SingleItemValidator<T>(private val message: String? = null) : Validator<Collection<T>>() {
    override fun validate(value: Collection<T>) {
        if (value.size != 1) {
            throw CollectionValidationException(
                message ?: "Collection should contain only one item"
            )
        }
    }
}