package ru.skillbranch.gameofthrones.data.remote.res

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HouseRes(
    @field:Json(name = "url") val url: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "region") val region: String,
    @field:Json(name = "coatOfArms") val coatOfArms: String,
    @field:Json(name = "words") val words: String,
    @field:Json(name = "titles") val titles: List<String> = listOf(),
    @field:Json(name = "seats") val seats: List<String> = listOf(),
    @field:Json(name = "currentLord") val currentLord: String,
    @field:Json(name = "heir") val heir: String,
    @field:Json(name = "overlord") val overlord: String,
    @field:Json(name = "founded") val founded: String,
    @field:Json(name = "founder") val founder: String,
    @field:Json(name = "diedOut") val diedOut: String,
    @field:Json(name = "ancestralWeapons") val ancestralWeapons: List<String> = listOf(),
    @field:Json(name = "cadetBranches") val cadetBranches: List<Any> = listOf(),
    @field:Json(name = "swornMembers") val swornMembers: List<String> = listOf()
)