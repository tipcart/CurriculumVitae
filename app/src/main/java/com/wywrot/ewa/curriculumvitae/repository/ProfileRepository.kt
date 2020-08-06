package com.wywrot.ewa.curriculumvitae.repository

import android.content.Context
import com.wywrot.ewa.curriculumvitae.rest.NetworkService
import java.net.UnknownHostException

class ProfileRepository private constructor(
    private val networkService: NetworkService
) {
    suspend fun getBaseInfo(): Int {
        try {
            val response = networkService.restService.getBaseInfo()
            if (response.isSuccessful && response.body() != null) {
                response.body()

            }

            return response.code()
        } catch (e: UnknownHostException) {
            println(e)
            return -2
        } catch (e: Exception) {
            println(e)
            return -1
        }
    }

    companion object {
        @Volatile
        private var instance: ProfileRepository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance
                    ?: ProfileRepository(
                        NetworkService
                    )
                        .also { instance = it }
            }
    }
}