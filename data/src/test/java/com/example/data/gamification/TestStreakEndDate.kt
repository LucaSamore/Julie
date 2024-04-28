package com.example.data.gamification

import com.example.data.util.today
import org.junit.Test

class TestStreakEndDate {

    @Test
    fun `Test streak end date is not valid`() {
        assert(EndDate(today().plusDays(1)).isLeft())
    }
}
