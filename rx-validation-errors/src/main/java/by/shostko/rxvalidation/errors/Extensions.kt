@file:Suppress("unused")

package by.shostko.rxvalidation.errors

import android.content.Context
import by.shostko.errors.Error
import by.shostko.errors.NoError
import by.shostko.rxvalidation.AbsValidation
import by.shostko.rxvalidation.ValidationResult
import io.reactivex.Flowable

fun AbsValidation<*>.getError(): Flowable<Error> = result.map(ValidationResult::getError)

fun ValidationResult.getError(): Error = when (this) {
    ValidationResult.Valid -> NoError
    is ValidationResult.Invalid -> Error.cast(throwable ?: Throwable())
}

fun ValidationResult.getText(context: Context): CharSequence = getError().text(context)

fun ValidationResult.getTextOrNull(context: Context): CharSequence? = getText(context).let { if (it.isBlank()) null else it }