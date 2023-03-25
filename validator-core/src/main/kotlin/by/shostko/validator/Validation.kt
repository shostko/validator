package by.shostko.validator

import kotlinx.coroutines.flow.*

class Validation<T : Any?, R : Any?>(
    validator: Validator<T, R>,
) {
    private val holderFlowInitializer = lazy { MutableStateFlow<Holder<T>>(Holder.Initial()) }
    private val holderFlowMutable by holderFlowInitializer
    private val holderFlow: StateFlow<Holder<T>> by lazy {
        if (!holderFlowInitializer.isInitialized()) {
            holderFlowMutable.value = _value
        }
        holderFlowMutable.asStateFlow()
    }
    val valueFlow: Flow<T> = holderFlow
        .filterNot { it is Holder.Initial }
        .map { it as Holder.WithValue }
        .map { it.value }

    // TODO switch to provided scope?
    val resultFlow: Flow<ValidationResult<T, R>> = valueFlow.map(validator)

    private var _value: Holder<T> = Holder.Initial()
        set(value) {
            field = value
            if (holderFlowInitializer.isInitialized()) {
                holderFlowMutable.value = value
            }
        }

    val value: T
        get() = _value.let { holder ->
            if (holder is Holder.WithValue) {
                holder.value
            } else {
                throw UnsupportedOperationException("Value was not yet provided!")
            }
        }
}

private sealed class Holder<T : Any?> {

    class Initial<T> : Holder<T>()

    data class WithValue<T>(
        val value: T,
    ) : Holder<T>()
}
