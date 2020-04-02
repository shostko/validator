@file:Suppress("unused")

package by.shostko.rxvalidation

import android.util.Patterns
import java.util.regex.Pattern

private class RegExpValidationException(message: String? = null) : ValidationException(message)

open class RegExpValidator<T : CharSequence>(private val pattern: Pattern, private val message: String? = null) : Validator<T>() {
    constructor(pattern: String, message: String? = null) : this(Pattern.compile(pattern), message)

    override fun validate(value: T) {
        if (!pattern.matcher(value).matches()) {
            throw RegExpValidationException(
                message ?: "Value does not match required pattern"
            )
        }
    }
}

class EmailValidator<T : CharSequence>(message: String? = null) : RegExpValidator<T>(Patterns.EMAIL_ADDRESS, message ?: "The e-mail address is not in a valid format")

class UrlValidator<T : CharSequence>(message: String? = null) : RegExpValidator<T>(Patterns.WEB_URL, message ?: "The URL is not in a valid format")

class PhoneValidator<T : CharSequence>(message: String? = null) : RegExpValidator<T>(Patterns.PHONE, message ?: "The phone number is not in a valid format")

class IpValidator<T : CharSequence>(message: String? = null) : RegExpValidator<T>(Patterns.IP_ADDRESS, message ?: "The ip address is not valid")

class HexValidator<T : CharSequence>(message: String? = null) : RegExpValidator<T>("^(#|)[0-9A-Fa-f]+\$", message ?: "The value has not only hexadecimal digit characters")