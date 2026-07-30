package com.colorverse.kids.core.data.repository

import androidx.compose.ui.graphics.Color
import com.colorverse.kids.core.data.local.database.SavedArtworkDao
import com.colorverse.kids.core.data.local.database.SavedArtworkEntity
import com.colorverse.kids.core.designsystem.*
import com.colorverse.kids.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ColoringRepository(private val savedArtworkDao: SavedArtworkDao) {

    fun getCategories(): List<Category> = listOf(
        Category("fruits", "Fruits", "Tasty & colorful fruits!", "🍎", ElectricCoral, 12, true),
        Category("vegetables", "Vegetables", "Healthy green veggies!", "🥦", MintMeadow, 10, true),
        Category("birds", "Birds", "Feathered flying friends!", "🦜", SunshineGold, 10, true),
        Category("animals", "Animals", "Wild & cute animals!", "🦁", ElectricCoral, 15, true),
        Category("flowers", "Flowers", "Beautiful blooming flowers!", "🌸", VioletGlow, 8),
        Category("nature", "Nature", "Mountains, sun & rivers!", "🌲", MintMeadow, 10),
        Category("vehicles", "Vehicles", "Cars, planes & rockets!", "🚀", SkyBlue, 12, true),
        Category("buildings", "Buildings", "Castles & towers!", "🏰", VioletGlow, 8),
        Category("space", "Space", "Stars, planets & UFOs!", "🌌", SkyBlue, 10, true),
        Category("ocean", "Ocean", "Dolphins & sea creatures!", "🐬", SkyBlue, 12, true),
        Category("food", "Food", "Ice cream & pizza!", "🍕", SunshineGold, 10),
        Category("festivals", "Festivals", "Cakes & fireworks!", "🎆", ElectricCoral, 8),
        Category("daily_objects", "Objects", "Clocks, lamps & toys!", "⏰", MintMeadow, 10),
        Category("professions", "Professions", "Doctors & astronauts!", "🧑‍🚒", VioletGlow, 10),
        Category("numbers", "Numbers", "Learn to count 0 to 9!", "🔢", SunshineGold, 10, true),
        Category("letters", "Letters", "Learn alphabets A to Z!", "🔤", ElectricCoral, 26, true)
    )

    fun getColoringPagesForCategory(categoryId: String): List<ColoringPage> {
        return (1..8).map { index ->
            ColoringPage(
                id = "${categoryId}_$index",
                title = "$categoryId Page #$index",
                categoryId = categoryId,
                difficulty = when (index % 3) {
                    0 -> Difficulty.EASY
                    1 -> Difficulty.MEDIUM
                    else -> Difficulty.HARD
                },
                iconEmoji = when (categoryId) {
                    "fruits" -> listOf("🍎", "🍌", "🍉", "🍓", "🍊", "🍍", "🍇", "🍒")[index - 1]
                    "animals" -> listOf("🦁", "🐘", "🦒", "🐒", "🐯", "🐻", "🦓", "🦘")[index - 1]
                    "vehicles" -> listOf("🚗", "✈️", "🚂", "🚀", "🚒", "🚓", "🚁", "⛵")[index - 1]
                    "space" -> listOf("👨‍🚀", "🪐", "🛸", "🚀", "⭐", "👾", "🌙", "☄️")[index - 1]
                    "ocean" -> listOf("🐬", "🐙", "🐢", "🐋", "🐠", "🪼", "🦭", "🦈")[index - 1]
                    else -> "🎨"
                },
                regions = listOf(
                    VectorRegion("r1", "Background", "", Color.White),
                    VectorRegion("r2", "Main Body", "", Color.White),
                    VectorRegion("r3", "Accent Detail", "", Color.White)
                )
            )
        }
    }

    val savedArtworks: Flow<List<SavedArtworkEntity>> = savedArtworkDao.getAllArtworks()

    suspend fun saveArtwork(artwork: SavedArtworkEntity) {
        savedArtworkDao.insertArtwork(artwork)
    }

    suspend fun deleteArtwork(id: String) {
        savedArtworkDao.deleteArtwork(id)
    }
}
