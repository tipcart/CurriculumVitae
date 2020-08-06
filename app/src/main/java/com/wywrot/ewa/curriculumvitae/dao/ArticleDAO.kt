package com.wywrot.ewa.curriculumvitae.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wywrot.ewa.curriculumvitae.rest.Article

@Dao
interface ArticleDAO {
    @Query("SELECT * from article")
    fun getArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)
}