package com.example.data

import com.example.data.user.EmailAddress
import org.junit.Test

class TestEmailAddress {

    @Test
    fun `Test email address is empty`() {
        assert(EmailAddress("").isLeft())
    }

    @Test
    fun `Test email address contains only white spaces`() {
        assert(EmailAddress("     ").isLeft())
    }

    @Test
    fun `Test email with no user`() {
        assert(EmailAddress("@gmail.com").isLeft())
    }

    @Test
    fun `Test email with no domain`() {
        assert(EmailAddress("user").isLeft())
    }

    @Test
    fun `Test email with no @ symbol`() {
        assert(EmailAddress("user.com").isLeft())
    }

    @Test
    fun `Test email with domain extension too short`() {
        assert(EmailAddress("user@gmail.c").isLeft())
    }

    @Test
    fun `Test email with domain too short`() {
        assert(EmailAddress("user@.com").isLeft())
    }

    @Test
    fun `Test email address is valid`() {
        assert(EmailAddress("test.123@studio.unibo.it").isRight())
    }
}
