package com.colorverse.kids

import android.app.Application
import com.colorverse.kids.core.data.local.database.AppDatabase
import com.colorverse.kids.core.data.local.database.UserProgressEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ColorVerseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Pre-seed default user progress asynchronously to guarantee crash-free startup
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val db = AppDatabase.getDatabase(applicationContext)
                val existing = db.userProgressDao().getUserProgressDirect()
                if (existing == null) {
                    db.userProgressDao().insertOrUpdate(
                        UserProgressEntity(
                            id = 1,
                            level = 1,
                            currentXp = 0,
                            requiredXp = 100,
                            streakDays = 1,
                            coins = 100,
                            stars = 15,
                            totalArtworksCompleted = 0
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
