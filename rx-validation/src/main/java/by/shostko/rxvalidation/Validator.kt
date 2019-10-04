@file:Suppress("unused")

package by.shostko.rxvalidation

import io.reactivex.Completable

abstract class Validator<T> : Validation.Delegate<T>() {

    @Throws(Throwable::class)
    abstract fun validate(value: T)

    final override val validator: Validator<T>
        get() = this

    internal fun validateAsCompletable(value: T): Completable = Completable.fromAction { validate(value) }

    abstract class Predicate<T>(private val message: String? = null) : Validator<T>() {
        final override fun validate(value: T) {
            if (!isValid(value)) {
                throw ValidationException(message)
            }
        }

        protected abstract fun isValid(value: T): Boolean
    }

    private class SimpleAction<T>(private val function: (T) -> Unit) : Validator<T>() {
        override fun validate(value: T) = function(value)
    }

    private class SimplePredicate<T>(message: String?, private val function: (T) -> Boolean) : Predicate<T>(message) {
        override fun isValid(value: T): Boolean = function(value)
    }

    companion object {
        fun <T> predicate(message: String?, function: (T) -> Boolean): Validator<T> = SimplePredicate(message, function)
        fun <T> predicate(function: (T) -> Boolean): Validator<T> = SimplePredicate(null, function)
        fun <T> simple(function: (T) -> Unit): Validator<T> = SimpleAction(function)
    }
}

private class CompositeValidator<T>(private vararg val validators: Validator<in T>) : Validator<T>() {
    override fun validate(value: T) = validators.forEach { it.validate(value) }
}

private class IterableValidator<T>(private val validators: Iterable<Validator<in T>>) : Validator<T>() {
    override fun validate(value: T) = validators.forEach { it.validate(value) }
}

fun <T> validators(vararg validators: Validator<in T>): Validator<T> = CompositeValidator(*validators)

fun <T> validators(vararg validators: (T) -> Boolean): Validator<T> = IterableValidator(validators.map(Validator.Companion::predicate))

fun <T> validators(validators: Iterable<Validator<in T>>): Validator<T> = IterableValidator(validators)