package com.colorverse.kids.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.colorverse.kids.core.designsystem.components.KidsNavigationBar
import com.colorverse.kids.feature.canvas.CanvasScreen
import com.colorverse.kids.feature.canvas.CanvasViewModel
import com.colorverse.kids.feature.categories.CategoriesScreen
import com.colorverse.kids.feature.categories.CategoriesViewModel
import com.colorverse.kids.feature.gallery.GalleryScreen
import com.colorverse.kids.feature.gallery.GalleryViewModel
import com.colorverse.kids.feature.gamification.AchievementsScreen
import com.colorverse.kids.feature.gamification.GamificationViewModel
import com.colorverse.kids.feature.home.HomeScreen
import com.colorverse.kids.feature.home.HomeViewModel
import com.colorverse.kids.feature.parent.ParentDashboardScreen
import com.colorverse.kids.feature.parent.ParentViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Categories : Screen("categories")
    object Canvas : Screen("canvas/{pageId}") {
        fun createRoute(pageId: String) = "canvas/$pageId"
    }
    object Gallery : Screen("gallery")
    object Gamification : Screen("gamification")
    object Parent : Screen("parent")
}

@Composable
fun ColorVerseNavHost(
    homeViewModel: HomeViewModel,
    canvasViewModel: CanvasViewModel,
    categoriesViewModel: CategoriesViewModel,
    galleryViewModel: GalleryViewModel,
    gamificationViewModel: GamificationViewModel,
    parentViewModel: ParentViewModel,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route
    val showBottomBar = !currentRoute.startsWith("canvas")

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                KidsNavigationBar(
                    currentRoute = currentRoute,
                    onNavigateToDestination = { dest ->
                        navController.navigate(dest.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = homeViewModel,
                    onCategoryClick = { categoryId ->
                        navController.navigate(Screen.Categories.route)
                    },
                    onStartColoring = { pageId ->
                        canvasViewModel.loadPage(pageId)
                        navController.navigate(Screen.Canvas.createRoute(pageId))
                    }
                )
            }

            composable(Screen.Categories.route) {
                CategoriesScreen(
                    viewModel = categoriesViewModel,
                    onSelectPage = { pageId ->
                        canvasViewModel.loadPage(pageId)
                        navController.navigate(Screen.Canvas.createRoute(pageId))
                    }
                )
            }

            composable(Screen.Canvas.route) { backStackEntry ->
                val pageId = backStackEntry.arguments?.getString("pageId") ?: "space_1"
                CanvasScreen(
                    viewModel = canvasViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Screen.Gallery.route) {
                GalleryScreen(
                    viewModel = galleryViewModel,
                    onReColorPage = { pageId ->
                        canvasViewModel.loadPage(pageId)
                        navController.navigate(Screen.Canvas.createRoute(pageId))
                    }
                )
            }

            composable(Screen.Gamification.route) {
                AchievementsScreen(viewModel = gamificationViewModel)
            }

            composable(Screen.Parent.route) {
                ParentDashboardScreen(
                    viewModel = parentViewModel,
                    onBackClick = { navController.navigate(Screen.Home.route) }
                )
            }
        }
    }
}
