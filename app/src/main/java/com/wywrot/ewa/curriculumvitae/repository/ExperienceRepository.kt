package com.wywrot.ewa.curriculumvitae.repository

import android.content.Context
import com.wywrot.ewa.curriculumvitae.dao.BaseInfoDAO
import com.wywrot.ewa.curriculumvitae.dao.ExperienceDAO
import com.wywrot.ewa.curriculumvitae.database.CurriculumVitaeDatabase
import com.wywrot.ewa.curriculumvitae.rest.NetworkService
import java.net.UnknownHostException

class ExperienceRepository private constructor(
    private val experienceDAO: ExperienceDAO
) {
    private val networkService = NetworkService

    fun getExperience() = experienceDAO.getExperience()

    suspend fun getExperienceFromRest(): Int {
        return try {
            val response = networkService.restService.getExperience()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.experiencesList?.forEach {
                    experienceDAO.insert(it)
                }
            }
            response.code()
        } catch (e: UnknownHostException) {
            println(e)
            -2
        } catch (e: Exception) {
            println(e)
            -1
        }
    }

    companion object {
        @Volatile
        private var instance: ExperienceRepository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ExperienceRepository(
                    CurriculumVitaeDatabase.getDatabase(context).getExperienceDao()
                ).also { instance = it }
            }
    }
}