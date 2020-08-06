package com.wywrot.ewa.curriculumvitae.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private const val baseURL = " https://gist.githubusercontent.com/tipcart/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(okHttpClientWithToken())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val restService: CurriculumVitaeApi = retrofit.create(CurriculumVitaeApi::class.java)

    private fun okHttpClientWithToken() =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .method(original.method, original.body)

                val request = requestBuilder.build()
                val response = chain.proceed(request)

                response
            }
            .build()
}
