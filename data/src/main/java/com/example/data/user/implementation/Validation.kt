package com.example.data.user.implementation

internal const val FIRST_LAST_NAME_MIN_LENGTH = 1

internal const val FIRST_LAST_NAME_MAX_LENGTH = 50

internal const val MIN_AGE = 6

internal const val MAX_AGE = 100

internal const val USERNAME_MIN_LENGTH = 2

internal const val USERNAME_MAX_LENGTH = 2

internal const val PASSWORD_MIN_LENGTH = 8

internal const val PASSWORD_MAX_LENGTH = 50

internal infix fun String.lengthIsBetween(range: IntRange): Boolean = length in range

internal fun String.isEmailValid(): Boolean =
    matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$\n"))

internal fun String.containsAtLeastOneNumber(): Boolean = matches(Regex(".*[0-9].*"))

internal fun String.containsAtLeastOneUpperCaseLetter(): Boolean = matches(Regex(".*[A-Z].*"))

internal fun String.containsAtLeastOneLowerCaseLetter(): Boolean = matches(Regex(".*[a-z].*"))

internal fun String.containsAtLeastOneLetterAndOneNumber(): Boolean =
    matches(Regex("(?=.*[0-9])(?=.*[a-zA-Z]).*"))