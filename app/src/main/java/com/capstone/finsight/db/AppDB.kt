package com.capstone.finsight.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


abstract class AppDB : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null
        @JvmStatic
        fun getDatabase(context: Context): AppDB {
            if (INSTANCE == null) {
                synchronized(AppDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDB::class.java, "app_db")
                        .build()
                }
            }
            return INSTANCE as AppDB
        }
    }
}