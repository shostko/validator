package by.shostko.validator

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class DelegatedValidator<THIS : Any, T : Any?, R : Any?>
    : Validator<T, R>, ReadOnlyProperty<THIS, Validation<T, R>> {

    private var value: Validation<T, R>? = null

    final override fun getValue(thisRef: THIS, property: KProperty<*>): Validation<T, R> {
        if (value == null) {
            synchronized(this) {
                if (value == null) {
                    value = Validation(this@DelegatedValidator)
                }
            }
        }
        return value!!
    }
}
