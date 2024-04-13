package com.example.data

import com.example.data.user.FirstName
import com.example.data.user.LastName
import org.junit.Test

class TestFirstAndLastNameValidation {

    @Test
    fun `Test first name is empty`() {
        assert(FirstName("").isLeft())
    }

    @Test
    fun `Test first name contains only white spaces`() {
        assert(FirstName("          ").isLeft())
    }

    @Test
    fun `Test first name is too short`() {
        assert(FirstName("a").isLeft())
    }

    @Test
    fun `Test first name is too long`() {
        assert(FirstName("abcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+?<>").isLeft())
    }

    @Test
    fun `Test first name contains only numbers`() {
        assert(FirstName("1644").isLeft())
    }

    @Test
    fun `Test first name is valid`() {
        assert(FirstName("test").isRight())
    }

    @Test
    fun `Test last name is empty`() {
        assert(LastName("").isLeft())
    }

    @Test
    fun `Test last name contains only white spaces`() {
        assert(LastName("      ").isLeft())
    }

    @Test
    fun `Test last name is too short`() {
        assert(LastName("b").isLeft())
    }

    @Test
    fun `Test last name is too long`() {
        assert(LastName("abcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+?<>").isLeft())
    }

    @Test
    fun `Test last name contains only numbers`() {
        assert(LastName("1644").isLeft())
    }

    @Test
    fun `Test last name is valid`() {
        assert(LastName("test").isRight())
    }
}
