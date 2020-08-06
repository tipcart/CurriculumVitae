package com.wywrot.ewa.curriculumvitae.repository

import android.content.Context
import com.wywrot.ewa.curriculumvitae.dao.BaseInfoDAO
import com.wywrot.ewa.curriculumvitae.database.CurriculumVitaeDatabase
import com.wywrot.ewa.curriculumvitae.rest.NetworkService
import java.net.UnknownHostException

class BaseInfoRepository private constructor(
    private val baseInfoDAO: BaseInfoDAO
) {
    private val networkService = NetworkService

    fun getBaseInfo() = baseInfoDAO.getBaseInfo()

    suspend fun getBaseInfoFromRest(): Int {
        return try {
            val response = networkService.restService.getBaseInfo()
            if (response.isSuccessful && response.body() != null) {
                baseInfoDAO.insert(response.body()!!)
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
        private var instance: BaseInfoRepository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: BaseInfoRepository(
                    CurriculumVitaeDatabase.getDatabase(context).getBadeInfoDao()
                ).also { instance = it }
            }
    }
}