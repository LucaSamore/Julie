package com.example.data.user

import java.time.LocalDate
import org.junit.Test

class TestBirthDate {

    @Test
    fun `Test birthdate is too young`() {
        assert(BirthDate(LocalDate.now()).isLeft())
    }

    @Test
    fun `Test birthdate is too old`() {
        assert(BirthDate(LocalDate.now().minusYears(101)).isLeft())
    }

    @Test
    fun `Test birthdate is valid`() {
        assert(BirthDate(LocalDate.of(2000, 1, 1)).isRight())
    }
}
