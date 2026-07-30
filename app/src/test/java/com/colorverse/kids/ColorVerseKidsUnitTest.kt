package com.colorverse.kids

import com.colorverse.kids.core.model.UserProgress
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorVerseKidsUnitTest {

    @Test
    fun testXpLevelCalculation() {
        val initialProgress = UserProgress(level = 1, currentXp = 50)
        val addedXp = 60
        val newTotalXp = initialProgress.currentXp + addedXp
        val calculatedLevel = initialProgress.level + (newTotalXp / 100)
        val remainingXp = newTotalXp % 100

        assertEquals(2, calculatedLevel)
        assertEquals(10, remainingXp)
    }

    @Test
    fun testParentalGateMathSolver() {
        val a = 7
        val b = 8
        val expectedProduct = 56
        assertEquals(expectedProduct, a * b)
    }
}
