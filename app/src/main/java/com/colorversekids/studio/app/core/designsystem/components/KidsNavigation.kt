package com.colorversekids.studio.app.core.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavDestination(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    HOME("home", "Home", Icons.Default.Home),
    CATEGORIES("categories", "Explore", Icons.Default.GridView),
    GALLERY("gallery", "Gallery", Icons.Default.PhotoLibrary),
    GAMIFICATION("gamification", "Rewards", Icons.Default.EmojiEvents),
    PARENT("parent", "Parent", Icons.Default.Shield)
}

@Composable
fun KidsNavigationBar(
    currentRoute: String,
    onNavigateToDestination: (NavDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        NavDestination.values().forEach { destination ->
            val selected = currentRoute == destination.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.title
                    )
                },
                label = {
                    Text(text = destination.title)
                }
            )
        }
    }
}
