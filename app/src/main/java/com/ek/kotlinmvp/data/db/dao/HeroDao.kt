package com.ek.kotlinmvp.data.db.dao

import androidx.room.*
import com.ek.kotlinmvp.data.db.entity.Hero

@Dao
interface HeroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHero(hero: Hero)

    @Update
    fun updateHero(hero: Hero)

    @Delete
    fun deleteHero(hero: Hero)

    @Query("SELECT * FROM Hero WHERE hero_page == :hero_page")
    fun getHeroesByPage(hero_page: Int): List<Hero>
}