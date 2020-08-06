package com.wywrot.ewa.curriculumvitae.rest

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "article")
class Article(
    @PrimaryKey
    @SerializedName("link")
    var link: String = "",
    @SerializedName("title")
    var title: String? = ""
)

class ArticlesList(
    @SerializedName("articleList")
    var articleList: List<Article>? = emptyList()
)