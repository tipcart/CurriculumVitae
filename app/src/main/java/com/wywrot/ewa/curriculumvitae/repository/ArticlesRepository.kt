package com.wywrot.ewa.curriculumvitae.repository

import android.content.Context
import com.wywrot.ewa.curriculumvitae.dao.ArticleDAO
import com.wywrot.ewa.curriculumvitae.database.CurriculumVitaeDatabase
import com.wywrot.ewa.curriculumvitae.rest.NetworkService
import java.net.UnknownHostException

class ArticlesRepository private constructor(
    private val articlesDAO: ArticleDAO
) {
    private val networkService = NetworkService

    fun getArticles() = articlesDAO.getArticles()

    suspend fun getArticlesFromRest(): Int {
        return try {
            val response = networkService.restService.getArticles()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.articleList?.forEach {
                    articlesDAO.insert(it)
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
        private var instance: ArticlesRepository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ArticlesRepository(
                    CurriculumVitaeDatabase.getDatabase(context).getArticleDao()
                ).also { instance = it }
            }
    }
}