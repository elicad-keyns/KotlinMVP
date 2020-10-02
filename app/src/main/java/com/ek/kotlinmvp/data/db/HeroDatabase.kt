package com.ek.kotlinmvp.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ek.kotlinmvp.data.common.RAM_BASE_DB_NAME
import com.ek.kotlinmvp.data.db.dao.HeroDao
import com.ek.kotlinmvp.data.db.entity.Hero
import com.ek.kotlinmvp.other.MainApplication

@Database(entities = [Hero::class], version = 1)
abstract class HeroDatabase: RoomDatabase() {
    abstract fun heroDao(): HeroDao

    companion object {
        var INSTANCE: HeroDatabase? = null

        fun getHeroDatabase(): HeroDatabase? {
            if (INSTANCE == null) {
                synchronized(HeroDatabase::class) {
                    INSTANCE = Room.databaseBuilder(MainApplication.context, HeroDatabase::class.java, RAM_BASE_DB_NAME)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}