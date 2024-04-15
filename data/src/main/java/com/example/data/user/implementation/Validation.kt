package com.example.data.user.implementation

import org.apache.commons.validator.routines.EmailValidator

internal const val NAME_MIN_LENGTH = 2

internal const val NAME_MAX_LENGTH = 50

internal const val MIN_AGE = 6

internal const val MAX_AGE = 100

internal const val USERNAME_MIN_LENGTH = 2

internal const val USERNAME_MAX_LENGTH = 8

internal const val PASSWORD_MIN_LENGTH = 6

internal const val PASSWORD_MAX_LENGTH = 4096

internal infix fun String.lengthIsBetween(range: IntRange): Boolean = length in range

internal fun String.isNumeric(): Boolean = matches(Regex("-?[0-9]+(\\\\.[0-9]+)?"))

internal fun String.isEmailValid(): Boolean = EmailValidator.getInstance().isValid(this)

internal fun String.containsAtLeastOneNumber(): Boolean = matches(Regex(".*[0-9].*"))

internal fun String.containsAtLeastOneUpperCaseLetter(): Boolean = matches(Regex(".*[A-Z].*"))

internal fun String.containsAtLeastOneLowerCaseLetter(): Boolean = matches(Regex(".*[a-z].*"))

internal fun String.containsAtLeastOneLetterAndOneNumber(): Boolean =
    matches(Regex("(?=.*[0-9])(?=.*[a-zA-Z]).*"))

internal fun String.containsAtLeastOneSpecialCharacter(): Boolean =
    matches(Regex("^(?=.*[!@#\$%^&*()_{}|:<>?~`\\-=\\\\;',./]).*\$"))
