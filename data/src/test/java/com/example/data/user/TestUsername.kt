package com.example.data.user

import org.junit.Test

class TestUsername {

    @Test
    fun `Test username is empty`() {
        assert(Username("").isLeft())
    }

    @Test
    fun `Test username contains only white spaces`() {
        assert(Username("       ").isLeft())
    }

    @Test
    fun `Test username is too short`() {
        assert(Username("a").isLeft())
    }

    @Test
    fun `Test username is too long`() {
        assert(Username("abcdefghi").isLeft())
    }

    @Test
    fun `Test username is valid`() {
        assert(Username("test").isRight())
    }
}
