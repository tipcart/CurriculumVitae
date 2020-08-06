package com.wywrot.ewa.curriculumvitae.rest

import retrofit2.Response
import retrofit2.http.GET

interface CurriculumVitaeApi {

    @GET("de3f325bb7e9b424f22d437ce4816e4b/raw/db376aaa0470b12495b461e1557af42c4485c1a7/baseInfo.json")
    suspend fun getBaseInfo(): Response<BaseInfo>

}