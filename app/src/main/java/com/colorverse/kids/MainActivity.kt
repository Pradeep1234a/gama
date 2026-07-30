package com.colorverse.kids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.colorverse.kids.core.data.local.database.AppDatabase
import com.colorverse.kids.core.data.local.datastore.UserPreferencesRepository
import com.colorverse.kids.core.data.repository.AchievementRepository
import com.colorverse.kids.core.data.repository.ColoringRepository
import com.colorverse.kids.core.data.repository.ProgressRepository
import com.colorverse.kids.core.designsystem.ColorVerseKidsTheme
import com.colorverse.kids.feature.canvas.CanvasViewModel
import com.colorverse.kids.feature.categories.CategoriesViewModel
import com.colorverse.kids.feature.gallery.GalleryViewModel
import com.colorverse.kids.feature.gamification.GamificationViewModel
import com.colorverse.kids.feature.home.HomeViewModel
import com.colorverse.kids.feature.parent.ParentViewModel
import com.colorverse.kids.navigation.ColorVerseNavHost

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
