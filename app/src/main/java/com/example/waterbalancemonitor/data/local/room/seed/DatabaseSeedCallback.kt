package com.example.waterbalancemonitor.data.local.room.seed

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseSeedCallback : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        DefaultSeedData.drinkTypes.forEach { drinkType ->
            db.execSQL(
                "INSERT OR IGNORE INTO drink_type " +
                    "(name, hydrationCoefficient, icon, isDefault) VALUES (?, ?, ?, ?)",
                arrayOf<Any?>(
                    drinkType.name,
                    drinkType.hydrationCoefficient,
                    drinkType.icon,
                    if (drinkType.isDefault) 1 else 0
                )
            )
        }
        DefaultSeedData.achievements.forEach { achievement ->
            db.execSQL(
                "INSERT OR IGNORE INTO achievement " +
                    "(code, title, description, category, conditionType, conditionValue, icon) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)",
                arrayOf<Any?>(
                    achievement.code,
                    achievement.title,
                    achievement.description,
                    achievement.category,
                    achievement.conditionType,
                    achievement.conditionValue,
                    achievement.icon
                )
            )
        }
    }
}
