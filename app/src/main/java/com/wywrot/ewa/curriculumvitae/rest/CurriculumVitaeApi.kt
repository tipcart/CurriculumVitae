package com.wywrot.ewa.curriculumvitae.rest

import retrofit2.Response
import retrofit2.http.GET

interface CurriculumVitaeApi {

    @GET("de3f325bb7e9b424f22d437ce4816e4b/raw/db376aaa0470b12495b461e1557af42c4485c1a7/baseInfo.json")
    suspend fun getBaseInfo(): Response<BaseInfo>

    @GET("822a93f0780932f83c2d5be0a532a1f7/raw/ce10513701f5c8b52ee1966224bb663e1042b830/experiense.json")
    suspend fun getExperience(): Response<ExperiencesList>

    @GET("90a99a505e2f99e37c28f7cb4dde27f5/raw/d408a72d87ec3fd47841b8d66e108d9cff250217/articles.json")
    suspend fun getArticles(): Response<ArticlesList>
}