@file:Suppress("unused")

package by.shostko.rxvalidation

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.FlowableProcessor
import org.reactivestreams.Subscriber
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class AbsValidation<T> {
    abstract val value: Flowable<T>
    abstract val result: Flowable<ValidationResult>
    val isValid: Flowable<Boolean>
        get() = result.map(ValidationResult::isValid)
}

abstract class BaseValidation<T>(private val validator: (T) -> Completable) : AbsValidation<T>() {
    final override val result: Flowable<ValidationResult> by lazy {
        value.switchMapSingle {
            validator(it)
                    .toSingleDefault(ValidationResult.Valid as ValidationResult)
                    .onErrorReturn { th -> ValidationResult.Invalid(th) }
        }.share()
    }
}

open class Validation<T> private constructor(
        private val processor: FlowableProcessor<T>,
        validator: (T) -> Completable
) : BaseValidation<T>(validator), Subscriber<T> by processor, Observer<T> {
    constructor(validator: (T) -> Completable) : this(BehaviorProcessor.create<T>(), validator)
    constructor(validator: Validator<T>) : this(BehaviorProcessor.create<T>(), { validator.validateAsCompletable(it) })

    override val value: Flowable<T>
        get() = processor.hide()

    override fun onSubscribe(d: Disposable) {
        if (processor.hasComplete() || processor.hasThrowable()) {
            d.dispose()
        }
    }

    class Predicate<T>(validator: (T) -> Single<Boolean>) : Validation<T>({
        Single.defer { validator(it) }
                .flatMapCompletable { if (it) Completable.complete() else Completable.error(ValidationException()) }
    })

    abstract class Delegate<T> : ReadOnlyProperty<Any, Validation<T>> {

        private var value: Validation<T>? = null

        abstract val validator: Validator<T>

        final override fun getValue(thisRef: Any, property: KProperty<*>): Validation<T> {
            if (value == null) {
                synchronized(this) {
                    if (value == null) {
                        value = Validation(validator)
                    }
                }
            }
            return value!!
        }
    }
}

sealed class ValidationResult {

    object Valid : ValidationResult() {
        override fun toString(): String = "Valid"
    }

    data class Invalid(val throwable: Throwable? = null) : ValidationResult() {
        override fun toString(): String = "Invalid($throwable)"
    }

    fun isValid(): Boolean = this === Valid

    companion object {
        fun from(valid: Boolean) = if (valid) Valid else Invalid()
    }
}

open class ValidationException(message: String? = null) : RuntimeException(message) {
    override fun toString(): String = if (message == null) javaClass.simpleName else "${javaClass.simpleName}($message)"
}