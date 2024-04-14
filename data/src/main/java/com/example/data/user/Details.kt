package com.example.data.user

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.data.gamification.today
import com.example.data.user.implementation.MAX_AGE
import com.example.data.user.implementation.MIN_AGE
import com.example.data.user.implementation.NAME_MAX_LENGTH
import com.example.data.user.implementation.NAME_MIN_LENGTH
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
    val interest: List<Interest>
)

data class Interest(val name: Name, val category: Category)

@JvmInline
value class FirstName private constructor(private val firstName: String) {
    companion object {
        operator fun invoke(firstName: String): Either<UserProblem, FirstName> = either {
            ensure(firstName.isNotBlank()) { FirstNameProblem("First name cannot be empty") }

            ensure(firstName lengthIsBetween NAME_MIN_LENGTH..NAME_MAX_LENGTH) {
                FirstNameProblem("First name cannot be more than 50 characters")
            }

            ensure(!firstName.isNumeric()) { FirstNameProblem("First name cannot be numbers only") }

            FirstName(firstName)
        }
    }
}

@JvmInline
value class LastName private constructor(private val lastName: String) {
    companion object {
        operator fun invoke(lastName: String): Either<UserProblem, LastName> = either {
            ensure(lastName.isNotBlank()) { LastNameProblem("Last name cannot be empty") }

            ensure(lastName lengthIsBetween NAME_MIN_LENGTH..NAME_MAX_LENGTH) {
                LastNameProblem("Last name cannot be more than 50 characters")
            }

            ensure(!lastName.isNumeric()) { LastNameProblem("Last name cannot be numbers only") }

            LastName(lastName)
        }
    }
}

@JvmInline
value class BirthDate private constructor(private val birthDate: LocalDate) {
    companion object {
        operator fun invoke(birthDate: LocalDate): Either<UserProblem, BirthDate> = either {
            ensure(birthDate.isBefore(today().minusYears(MIN_AGE.toLong()))) {
                BirthDateProblem("Birth date cannot be too young")
            }

            ensure(birthDate.isAfter(today().minusYears(MAX_AGE.toLong()))) {
                BirthDateProblem("Birth date cannot be too old")
            }

            BirthDate(birthDate)
        }
    }
}

@JvmInline
value class Username private constructor(private val username: String) {
    companion object {
        operator fun invoke(username: String): Either<UserProblem, Username> = either {
            ensure(username.isNotBlank()) { UsernameProblem("Username cannot be empty") }

            ensure(username lengthIsBetween USERNAME_MIN_LENGTH..USERNAME_MAX_LENGTH) {
                UsernameProblem(
                    "Username length must be between $USERNAME_MIN_LENGTH and $USERNAME_MAX_LENGTH"
                )
            }

            Username(username)
        }
    }
}

@JvmInline
value class EmailAddress private constructor(val emailAddress: String) {
    companion object {
        operator fun invoke(emailAddress: String): Either<UserProblem, EmailAddress> = either {
            ensure(emailAddress.isNotBlank()) {
                EmailAddressProblem("Email address cannot be empty")
            }

            ensure(emailAddress.isEmailValid()) { EmailAddressProblem("Email is not valid") }

            EmailAddress(emailAddress)
        }
    }
}

@JvmInline
value class Password private constructor(val password: String) {
    companion object {
        operator fun invoke(password: String): Either<UserProblem, Password> = either {
            ensure(password.isNotBlank()) { PasswordProblem("Password cannot be empty") }

            ensure(password lengthIsBetween PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH) {
                PasswordProblem(
                    "Password length must be between $PASSWORD_MIN_LENGTH and $PASSWORD_MAX_LENGTH"
                )
            }

            ensure(password.containsAtLeastOneNumber()) {
                PasswordProblem("Password must contain at least one number")
            }

            ensure(password.containsAtLeastOneUpperCaseLetter()) {
                PasswordProblem("Password must contain at least one upper case letter")
            }

            ensure(password.containsAtLeastOneLowerCaseLetter()) {
                PasswordProblem("Password must contain at least one lower case letter")
            }

            ensure(password.containsAtLeastOneLetterAndOneNumber()) {
                PasswordProblem("Password must contain at least one letter and one number")
            }

            ensure(password.containsAtLeastOneSpecialCharacter()) {
                PasswordProblem("Password must contain at least one special character")
            }

            Password(password)
        }
    }
}

@JvmInline value class Name(private val name: String)

@JvmInline value class Category(private val category: String)
