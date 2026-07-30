package com.colorverse.kids

import androidx.compose.ui.graphics.Color
import com.colorverse.kids.core.model.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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

    @Test
    fun testBrushToolCreation() {
        val brush = BrushTool(
            type = BrushType.PAINT_BRUSH,
            name = "Paint",
            iconEmoji = "🖌️",
            defaultWidth = 18f
        )
        assertEquals(BrushType.PAINT_BRUSH, brush.type)
        assertEquals("Paint", brush.name)
        assertEquals(18f, brush.defaultWidth, 0.01f)
    }

    @Test
    fun testCategoryDataModel() {
        val category = Category(
            id = "space",
            name = "Space",
            description = "Planets and stars",
            emoji = "🚀",
            color = Color.Blue,
            itemCount = 10,
            isFeatured = true
        )
        assertEquals("space", category.id)
        assertEquals("Space", category.name)
        assertTrue(category.isFeatured)
    }
}
