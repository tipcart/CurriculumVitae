package com.wywrot.ewa.curriculumvitae.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wywrot.ewa.curriculumvitae.rest.Experience

@Dao
interface ExperienceDAO {
    @Query("SELECT * from experience")
    fun getExperience(): LiveData<List<Experience>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(experience: Experience)
}