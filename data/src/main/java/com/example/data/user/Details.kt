package com.example.data.user

import arrow.core.Either
import arrow.core.NonEmptySet
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.data.gamification.today
import com.example.data.user.implementation.FIRST_LAST_NAME_MAX_LENGTH
import com.example.data.user.implementation.FIRST_LAST_NAME_MIN_LENGTH
import com.example.data.user.implementation.MAX_AGE
import com.example.data.user.implementation.MIN_AGE
import com.example.data.user.implementation.PASSWORD_MAX_LENGTH
import com.example.data.user.implementation.PASSWORD_MIN_LENGTH
import com.example.data.user.implementation.USERNAME_MAX_LENGTH
import com.example.data.user.implementation.USERNAME_MIN_LENGTH
import com.example.data.user.implementation.containsAtLeastOneLetterAndOneNumber
import com.example.data.user.implementation.containsAtLeastOneLowerCaseLetter
import com.example.data.user.implementation.containsAtLeastOneNumber
import com.example.data.user.implementation.containsAtLeastOneSpecialCharacter
import com.example.data.user.implementation.containsAtLeastOneUpperCaseLetter
import com.example.data.user.implementation.isEmailValid
import com.example.data.user.implementation.isNumeric
import com.example.data.user.implementation.lengthIsBetween
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
            ensure(firstName.isNotBlank()) { "First name cannot be empty" }

            ensure(
                firstName lengthIsBetween FIRST_LAST_NAME_MIN_LENGTH..FIRST_LAST_NAME_MAX_LENGTH
            ) {
                "First name cannot be more than 50 characters"
            }

            ensure(!firstName.isNumeric()) { "First name cannot be numbers only" }

            FirstName(firstName)
        }
    }
}

@JvmInline
value class LastName private constructor(private val lastName: String) {
    companion object {
        operator fun invoke(lastName: String): Either<String, LastName> = either {
            ensure(lastName.isNotBlank()) { "First name cannot be empty" }

            ensure(
                lastName lengthIsBetween FIRST_LAST_NAME_MIN_LENGTH..FIRST_LAST_NAME_MAX_LENGTH
            ) {
                "First name cannot be more than 50 characters"
            }

            ensure(!lastName.isNumeric()) { "First name cannot be numbers only" }

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

            ensure(birthDate.isAfter(today().minusYears(MAX_AGE.toLong()))) {
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

            ensure(password.containsAtLeastOneSpecialCharacter()) {
                "Password must contain at least one special character"
            }

            Password(password)
        }
    }
}

@JvmInline value class Name(private val name: String)

@JvmInline value class Category(private val category: String)
