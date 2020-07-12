package ru.skillbranch.gameofthrones.repositories

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.*
import ru.skillbranch.gameofthrones.data.CharacterResToCharacter
import ru.skillbranch.gameofthrones.data.HouseResToHouse
import ru.skillbranch.gameofthrones.data.local.AppDatabase
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.remote.FIRST_PAGE_INDEX
import ru.skillbranch.gameofthrones.data.remote.MAX_ITEMS_PER_PAGE
import ru.skillbranch.gameofthrones.data.remote.iceAndFireService
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import java.lang.Exception

object RootRepository {
    private val repositoryCS = CoroutineScope(Dispatchers.IO)

    /**
     * Получение данных о всех домах из сети
     * @param result - колбек содержащий в себе список данных о домах
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getAllHouses(result : (houses : List<HouseRes>) -> Unit) {
        repositoryCS.launch {
            val res = mutableListOf<HouseRes>()
            var currPage = FIRST_PAGE_INDEX
            var prevPageCnt = MAX_ITEMS_PER_PAGE
            while (prevPageCnt == MAX_ITEMS_PER_PAGE) {
                val newHouses = iceAndFireService.getAllHouses(currPage, MAX_ITEMS_PER_PAGE)
                prevPageCnt = newHouses.size
                currPage++
                res.addAll(newHouses)
            }

            result(res)
        }
    }

    /**
     * Получение данных о требуемых домах по их полным именам из сети 
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о домах
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouses(vararg houseNames: String, result : (houses : List<HouseRes>) -> Unit) {
        repositoryCS.launch {
            val res = houseNames.mapNotNull { houseName ->
                iceAndFireService.getHouseByName(houseName).getOrNull(0)
            }
            result(res)
        }
    }

    /**
     * Получение данных о требуемых домах по их полным именам и персонажах в каждом из домов из сети
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о доме и персонажей в нем (Дом - Список Персонажей в нем)
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouseWithCharacters(vararg houseNames: String, result : (houses : List<Pair<HouseRes, List<CharacterRes>>>) -> Unit) {
        repositoryCS.launch {
            val res = houseNames.mapNotNull { houseName ->
                val house = iceAndFireService.getHouseByName(houseName).getOrNull(0) ?: return@mapNotNull null
                val members = house.swornMembers.mapNotNull {
                    try {
                        it.substring(it.lastIndexOf("/") + 1).toInt()
                    } catch (ex: Exception) {
                        null
                    }
                }.map {
                    iceAndFireService.getCharacterById(it)
                }
                house to members
            }
            result(res)
        }
    }

    /**
     * Запись данных о домах в DB
     * @param houses - Список персонажей (модель HouseRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertHouses(houses : List<HouseRes>, complete: () -> Unit) {
        repositoryCS.launch {
            val convertedHouses = houses.map { HouseResToHouse(it) }
            AppDatabase.getInstance().HouseDao().insertAll(convertedHouses)
            complete()
        }
    }

    /**
     * Запись данных о пересонажах в DB
     * @param Characters - Список персонажей (модель CharacterRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertCharacters(Characters : List<CharacterRes>, complete: () -> Unit) {
        repositoryCS.launch {
            val convertedCharacters = Characters.map { CharacterResToCharacter(it) }
            AppDatabase.getInstance().CharacterDao().insertAll(convertedCharacters)
            complete()
        }
    }

    /**
     * При вызове данного метода необходимо выполнить удаление всех записей в db
     * @param complete - колбек о завершении очистки db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun dropDb(complete: () -> Unit) {
        //TODO 123
        AppDatabase.getInstance().clearAllTables()
        complete()
    }

    /**
     * Поиск всех персонажей по имени дома, должен вернуть список краткой информации о персонажах
     * дома - смотри модель CharacterItem
     * @param name - краткое имя дома (его первычный ключ)
     * @param result - колбек содержащий в себе список краткой информации о персонажах дома
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharactersByHouseName(name : String, result: (characters : List<CharacterItem>) -> Unit) {
        //TODO implement me
    }

    /**
     * Поиск персонажа по его идентификатору, должен вернуть полную информацию о персонаже
     * и его родственных отношения - смотри модель CharacterFull
     * @param id - идентификатор персонажа
     * @param result - колбек содержащий в себе полную информацию о персонаже
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharacterFullById(id : String, result: (character : CharacterFull) -> Unit) {
        //TODO implement me
    }

    /**
     * Метод возвращет true если в базе нет ни одной записи, иначе false
     * @param result - колбек о завершении очистки db
     */
    fun isNeedUpdate(result: (isNeed : Boolean) -> Unit){
        //TODO implement me
    }

}