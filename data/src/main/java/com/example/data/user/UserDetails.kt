package com.example.data.user

import androidx.core.text.isDigitsOnly
import arrow.core.Either
import arrow.core.NonEmptySet
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.data.gamification.today
import java.time.LocalDate

data class UserDetails(
    val firstName: FirstName,
    val lastName: LastName,
    val birthDate: BirthDate,
    val username: Username,
    val emailAddress: EmailAddress,
    val password: Password,
    val interest: NonEmptySet<Interest>
)

data class Interest(val name: Name, val category: Category)

@JvmInline
value class FirstName private constructor(private val firstName: String) {
    companion object {
        operator fun invoke(firstName: String): Either<String, FirstName> = either {
            ensure(firstName.isNotBlank()) {
                "First name cannot be empty"
            }

            ensure(firstName lengthIsBetween FIRST_LAST_NAME_MIN_LENGTH..FIRST_LAST_NAME_MAX_LENGTH) {
                "First name cannot be more than 50 characters"
            }

            ensure(!firstName.isDigitsOnly()) {
                "First name cannot be numbers only"
            }

            FirstName(firstName)
        }
    }
}

@JvmInline
value class LastName private constructor(private val lastName: String) {
    companion object {
        operator fun invoke(lastName: String): Either<String, LastName> = either {
            ensure(lastName.isNotBlank()) { "First name cannot be empty" }

            ensure(lastName lengthIsBetween FIRST_LAST_NAME_MIN_LENGTH..FIRST_LAST_NAME_MAX_LENGTH) {
                "First name cannot be more than 50 characters"
            }

            ensure(!lastName.isDigitsOnly()) {
                "First name cannot be numbers only"
            }

            LastName(lastName)
        }
    }
}

@JvmInline
value class BirthDate private constructor(private val birthDate: LocalDate) {
    companion object {
        operator fun invoke(birthDate: LocalDate): Either<String, BirthDate> = either {
            ensure(birthDate.isBefore(today().minusYears(MIN_AGE.toLong()))) {
                "Birth date cannot be too young"
            }

            ensure(birthDate.isAfter(today().plusYears(MAX_AGE.toLong()))) {
                "Birth date cannot be too old"
            }

            BirthDate(birthDate)
        }
    }
}

@JvmInline
value class Username private constructor(private val username: String) {
    companion object {
        operator fun invoke(username: String): Either<String, Username> = either {
            ensure(username.isNotBlank()) { "Username cannot be empty" }

            ensure(username lengthIsBetween USERNAME_MIN_LENGTH..USERNAME_MAX_LENGTH) {
                "Username length must be between $USERNAME_MIN_LENGTH and $USERNAME_MAX_LENGTH"
            }

            Username(username)
        }
    }
}

@JvmInline
value class EmailAddress private constructor(private val emailAddress: String) {
    companion object {
        operator fun invoke(emailAddress: String): Either<String, EmailAddress> = either {
            ensure(emailAddress.isNotBlank()) { "Email address cannot be empty" }

            ensure(emailAddress.isEmailValid()) { "Email is not valid" }

            EmailAddress(emailAddress)
        }
    }
}

@JvmInline
value class Password private constructor(private val password: String) {
    companion object {
        operator fun invoke(password: String): Either<String, Password> = either {
            ensure(password.isNotBlank()) { "Password cannot be empty" }

            ensure(password lengthIsBetween PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH) {
                "Password length must be between $PASSWORD_MIN_LENGTH and $PASSWORD_MAX_LENGTH"
            }

            ensure(password.containsAtLeastOneNumber()) {
                "Password must contain at least one number"
            }

            ensure(password.containsAtLeastOneUpperCaseLetter()) {
                "Password must contain at least one upper case letter"
            }

            ensure(password.containsAtLeastOneLowerCaseLetter()) {
                "Password must contain at least one lower case letter"
            }

            ensure(password.containsAtLeastOneLetterAndOneNumber()) {
                "Password must contain at least one letter and one number"
            }

            Password(password)
        }
    }
}

@JvmInline value class Name(private val name: String)

@JvmInline value class Category(private val category: String)

private const val FIRST_LAST_NAME_MIN_LENGTH = 1

private const val FIRST_LAST_NAME_MAX_LENGTH = 50

private const val MIN_AGE = 6

private const val MAX_AGE = 100

private const val USERNAME_MIN_LENGTH = 2

private const val USERNAME_MAX_LENGTH = 2

private const val PASSWORD_MIN_LENGTH = 8

private const val PASSWORD_MAX_LENGTH = 50

private infix fun String.lengthIsBetween(range: IntRange): Boolean = length in range

private fun String.isEmailValid(): Boolean =
    matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$\n"))

private fun String.containsAtLeastOneNumber(): Boolean = matches(Regex(".*[0-9].*"))

private fun String.containsAtLeastOneUpperCaseLetter(): Boolean = matches(Regex(".*[A-Z].*"))

private fun String.containsAtLeastOneLowerCaseLetter(): Boolean = matches(Regex(".*[a-z].*"))

private fun String.containsAtLeastOneLetterAndOneNumber(): Boolean =
    matches(Regex("(?=.*[0-9])(?=.*[a-zA-Z]).*"))
