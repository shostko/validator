@file:Suppress("unused")

package by.shostko.validator.strings

import java.util.regex.Pattern

open class RegExpValidator<T : CharSequence>(
    private val tag: String? = null,
    private val pattern: Pattern,
    private val reason: ((T) -> String?)? = null,
) : BaseValidator<T>(tag) {

    constructor(
        tag: String? = null,
        pattern: String,
        reason: ((T) -> String?)? = null,
    ) : this(tag, Pattern.compile(pattern), reason)

    override fun validate(value: T) {
        if (!pattern.matcher(value).matches()) {
            throw ValidationException(tag, reason?.invoke(value) ?: "Value does not match required pattern")
        }
    }
}

class EmailValidator<T : CharSequence>(
    tag: String? = null,
    reason: ((T) -> String?)? = null,
) : RegExpValidator<T>(tag, EMAIL_ADDRESS, reason ?: { "The e-mail address is not in a valid format" })

class PhoneValidator<T : CharSequence>(
    tag: String? = null,
    reason: ((T) -> String?)? = null,
) : RegExpValidator<T>(tag, PHONE, reason ?: { "The phone number is not in a valid format" })

class IpValidator<T : CharSequence>(
    tag: String? = null,
    reason: ((T) -> String?)? = null,
) : RegExpValidator<T>(tag, IP_ADDRESS, reason ?: { "The ip address is not valid" })

class HexValidator<T : CharSequence>(
    tag: String? = null,
    reason: ((T) -> String?)? = null,
) : RegExpValidator<T>(tag, HEX, reason ?: { "The value has not only hexadecimal digit characters" })

private const val EMAIL_ADDRESS =
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"

private const val PHONE =
    "(\\+[0-9]+[\\- \\.]*)?" +
        "(\\([0-9]+\\)[\\- \\.]*)?" +
        "([0-9][0-9\\- \\.]+[0-9])"

private const val IP_ADDRESS =
    "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]" +
        "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]" +
        "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}" +
        "|[1-9][0-9]|[0-9]))"

private const val HEX = "^(#|)[0-9A-Fa-f]+\$"
