package com.example.data.user

import org.junit.Test

class TestPassword {

    @Test
    fun `Test password is empty`() {
        assert(Password("").isLeft())
    }

    @Test
    fun `Test password contains only white spaces`() {
        assert(Password("     ").isLeft())
    }

    @Test
    fun `Test password is too short`() {
        assert(Password("test").isLeft())
    }

    @Test
    fun `Test password is too long`() {
        assert(Password("abcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+?<>{}").isLeft())
    }

    @Test
    fun `Test password contains at least one number`() {
        assert(Password("TestTest!").isLeft())
    }

    @Test
    fun `Test password contains at least one upper case letter`() {
        assert(Password("test123!").isLeft())
    }

    @Test
    fun `Test password contains at least one lower case letter`() {
        assert(Password("TEST123!").isLeft())
    }

    @Test
    fun `Test password contains at least one letter and one number`() {
        assert(Password("0123456789").isLeft())
    }

    @Test
    fun `Test password contains at least one special character`() {
        assert(Password("Test1234").isLeft())
    }

    @Test
    fun `Test password is valid`() {
        assert(Password("Test123!").isRight())
    }
}
