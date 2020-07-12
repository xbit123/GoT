package ru.skillbranch.gameofthrones.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

interface IceAndFireService {
    @GET("houses")
    suspend fun getAllHouses(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<HouseRes>

    @GET("houses")
    suspend fun getHouseByName(
        @Query("name") name: String
    ): List<HouseRes>

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterRes
}

const val FIRST_PAGE_INDEX = 1
const val MAX_ITEMS_PER_PAGE = 50

private val okHttpClient = OkHttpClient()

val iceAndFireService = Retrofit.Builder()
    .baseUrl(AppConfig.BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(IceAndFireService::class.java)