package com.wywrot.ewa.curriculumvitae.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wywrot.ewa.curriculumvitae.dao.BaseInfoDAO
import com.wywrot.ewa.curriculumvitae.dao.ExperienceDAO
import com.wywrot.ewa.curriculumvitae.rest.BaseInfo
import com.wywrot.ewa.curriculumvitae.rest.Experience

@Database(
    entities = [BaseInfo::class, Experience::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CurriculumVitaeDatabase : RoomDatabase() {
    abstract fun getBadeInfoDao(): BaseInfoDAO
    abstract fun getExperienceDao(): ExperienceDAO

    companion object {
        @Volatile
        private var INSTANCE: CurriculumVitaeDatabase? = null

        fun getDatabase(context: Context): CurriculumVitaeDatabase {
            return INSTANCE
                ?: synchronized(this) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        CurriculumVitaeDatabase::class.java,
                        "curriculum_vitae_database"
                    ).build().also { INSTANCE = it }
                }
        }
    }
}