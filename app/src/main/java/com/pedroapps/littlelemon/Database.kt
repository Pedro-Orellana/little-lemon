package com.pedroapps.littlelemon

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMenu(menuItems: List<MenuItemRoom>)

    @Query("SELECT * FROM menu_item")
    fun getAllMenuItems(): LiveData<List<MenuItemRoom>>

}


@Entity(tableName = "menu_item")
data class MenuItemRoom(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "category") val category: String
)

@Database(entities = [MenuItemRoom::class], version = 1, exportSchema = false)

abstract class MenuDatabase : RoomDatabase() {

    abstract fun menuDao(): MenuDao

    companion object {

        @Volatile
        private var Instance: MenuDatabase? = null

        fun getDatabase(context: Context): MenuDatabase {

            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MenuDatabase::class.java,
                    "menu_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                Instance = instance
                instance
            }
        }
    }
}

