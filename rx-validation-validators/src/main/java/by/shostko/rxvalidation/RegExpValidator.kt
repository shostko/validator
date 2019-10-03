@file:Suppress("unused")

package by.shostko.rxvalidation

import android.util.Patterns
import java.util.regex.Pattern

private class RegExpValidationException(message: String? = null) : ValidationException(message)

open class RegExpValidator(private val pattern: Pattern, private val message: String? = null) : Validator<CharSequence>() {
    constructor(pattern: String, message: String? = null) : this(Pattern.compile(pattern), message)

    override fun validate(value: CharSequence) {
        if (!pattern.matcher(value).matches()) {
            throw RegExpValidationException(
                message ?: "Value does not match required pattern"
            )
        }
    }
}

class EmailValidator(message: String? = null) : RegExpValidator(Patterns.EMAIL_ADDRESS, message ?: "The e-mail address is not in a valid format")

class UrlValidator(message: String? = null) : RegExpValidator(Patterns.WEB_URL, message ?: "The URL is not in a valid format")

class PhoneValidator(message: String? = null) : RegExpValidator(Patterns.PHONE, message ?: "The phone number is not in a valid format")

class IpValidator(message: String? = null) : RegExpValidator(Patterns.IP_ADDRESS, message ?: "The ip address is not valid")

class HexValidator(message: String? = null) : RegExpValidator("^(#|)[0-9A-Fa-f]+\$", message ?: "The value has not only hexadecimal digit characters")