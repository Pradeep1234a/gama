package com.colorversekids.studio.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.colorversekids.studio.app.core.data.local.database.AppDatabase
import com.colorversekids.studio.app.core.data.local.datastore.UserPreferencesRepository
import com.colorversekids.studio.app.core.data.repository.AchievementRepository
import com.colorversekids.studio.app.core.data.repository.ColoringRepository
import com.colorversekids.studio.app.core.data.repository.ProgressRepository
import com.colorversekids.studio.app.core.designsystem.ColorVerseKidsTheme
import com.colorversekids.studio.app.feature.canvas.CanvasViewModel
import com.colorversekids.studio.app.feature.categories.CategoriesViewModel
import com.colorversekids.studio.app.feature.gallery.GalleryViewModel
import com.colorversekids.studio.app.feature.gamification.GamificationViewModel
import com.colorversekids.studio.app.feature.home.HomeViewModel
import com.colorversekids.studio.app.feature.parent.ParentViewModel
import com.colorversekids.studio.app.navigation.ColorVerseNavHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val coloringRepository = ColoringRepository(database.savedArtworkDao())
        val progressRepository = ProgressRepository(database.userProgressDao())
        val achievementRepository = AchievementRepository(database.achievementDao())
        val userPreferencesRepository = UserPreferencesRepository(applicationContext)

        val homeViewModel = HomeViewModel(coloringRepository, progressRepository)
        val canvasViewModel = CanvasViewModel(coloringRepository, progressRepository)
        val categoriesViewModel = CategoriesViewModel(coloringRepository)
        val galleryViewModel = GalleryViewModel(coloringRepository)
        val gamificationViewModel = GamificationViewModel(progressRepository, achievementRepository)
        val parentViewModel = ParentViewModel(userPreferencesRepository)

        setContent {
            ColorVerseKidsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ColorVerseNavHost(
                        homeViewModel = homeViewModel,
                        canvasViewModel = canvasViewModel,
                        categoriesViewModel = categoriesViewModel,
                        galleryViewModel = galleryViewModel,
                        gamificationViewModel = gamificationViewModel,
                        parentViewModel = parentViewModel
                    )
                }
            }
        }
    }
}
