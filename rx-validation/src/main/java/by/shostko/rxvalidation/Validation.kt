@file:Suppress("unused")

package by.shostko.rxvalidation

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

abstract class BaseValidation<T> internal constructor(private val validator: (T) -> Flowable<ValidationResult>) : AbsValidation<T>() {
    final override val result: Flowable<ValidationResult> by lazy {
        value.switchMap {
            validator(it).onErrorReturn { throwable -> ValidationResult.Invalid(throwable) }
        }.share()
    }
}

open class Validation<T> private constructor(
    private val processor: FlowableProcessor<T>,
    validator: (T) -> Flowable<ValidationResult>
) : BaseValidation<T>(validator), Subscriber<T> by processor, Observer<T> {
    constructor(validator: (T) -> Flowable<ValidationResult>) : this(BehaviorProcessor.create<T>(), validator)
    constructor(validator: Validator<T>) : this(BehaviorProcessor.create<T>(), { validator.validateAsFlowable(it) })

    override val value: Flowable<T>
        get() = processor.hide()

    override fun onSubscribe(d: Disposable) {
        if (processor.hasComplete() || processor.hasThrowable()) {
            d.dispose()
        }
    }

    class Predicate<T>(validator: (T) -> Single<Boolean>) : Validation<T>({
        validator(it).map { result -> ValidationResult.from(result) }.toFlowable()
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

    fun getReason(): Throwable? = if (this is Invalid) this.throwable else null

    companion object {
        fun from(valid: Boolean) = if (valid) Valid else Invalid()
    }
}

open class ValidationException(message: String? = null) : RuntimeException(message) {
    override fun toString(): String = if (message == null) javaClass.simpleName else "${javaClass.simpleName}($message)"
}