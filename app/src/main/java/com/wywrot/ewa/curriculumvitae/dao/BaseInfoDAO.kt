package com.wywrot.ewa.curriculumvitae.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wywrot.ewa.curriculumvitae.rest.BaseInfo

@Dao
interface BaseInfoDAO {
    @Query("SELECT * from base_info LIMIT 1")
    fun getBaseInfo(): LiveData<BaseInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(baseInfo: BaseInfo)
}