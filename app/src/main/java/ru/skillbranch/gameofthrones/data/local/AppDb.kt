package ru.skillbranch.gameofthrones.data.local

import androidx.room.*
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.data.local.entities.House

@Database(entities = arrayOf(Character::class, House::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CharacterDao(): CharacterDao
    abstract fun HouseDao(): HouseDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(): AppDatabase = instance ?: synchronized(this) {
            instance ?: buildDatabase().also { instance = it }
        }

        private fun buildDatabase(): AppDatabase {
            return Room.databaseBuilder(
                App.appContext,
                AppDatabase::class.java,
                "db"
            ).build()
        }
    }
}

@Dao
interface HouseDao {
//    @Query("SELECT * FROM user")
//    fun getAll(): List<User>
//
//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query(
//        "SELECT * FROM user WHERE first_name LIKE :first AND " +
//                "last_name LIKE :last LIMIT 1"
//    )
//    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(houses: List<House>)

//    @Delete
//    fun delete(user: User)
}

@Dao
interface CharacterDao {
    @Insert
    fun insertAll(characters: List<ru.skillbranch.gameofthrones.data.local.entities.Character>)
}