@file:Suppress("unused")

package by.shostko.rxvalidation

import android.util.Patterns
import by.shostko.errors.ErrorCode
import by.shostko.rxvalidation.errors.BaseValidationErrorCode
import by.shostko.rxvalidation.errors.ValidationError
import java.util.regex.Pattern

private class RegExpValidationException(code: ErrorCode) : ValidationError(code)

open class RegExpValidator(private val pattern: Pattern, private val code: ErrorCode? = null) : Validator<CharSequence>() {
    constructor(pattern: String, code: ErrorCode? = null) : this(Pattern.compile(pattern), code)

    override fun validate(value: CharSequence) {
        if (!pattern.matcher(value).matches()) {
            throw RegExpValidationException(code ?: BaseValidationErrorCode(this, "Value does not match required pattern"))
        }
    }
}

class EmailValidator(code: ErrorCode? = null) : RegExpValidator(Patterns.EMAIL_ADDRESS, code ?: BaseValidationErrorCode(EmailValidator::class.java, "The e-mail address is not in a valid format"))

class UrlValidator(code: ErrorCode? = null) : RegExpValidator(Patterns.WEB_URL, code ?: BaseValidationErrorCode(UrlValidator::class.java, "The URL is not in a valid format"))

class PhoneValidator(code: ErrorCode? = null) : RegExpValidator(Patterns.PHONE, code ?: BaseValidationErrorCode(PhoneValidator::class.java, "The phone number is not in a valid format"))

class IpValidator(code: ErrorCode? = null) : RegExpValidator(Patterns.IP_ADDRESS, code ?: BaseValidationErrorCode(IpValidator::class.java, "The ip address is not valid"))

class HexValidator(code: ErrorCode? = null) : RegExpValidator("^(#|)[0-9A-Fa-f]+\$", code ?: BaseValidationErrorCode(HexValidator::class.java, "The value has not only hexadecimal digit characters"))