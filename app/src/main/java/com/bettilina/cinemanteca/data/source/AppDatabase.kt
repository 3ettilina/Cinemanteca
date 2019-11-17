package com.bettilina.cinemanteca.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bettilina.cinemanteca.data.dao.MovieDao
import com.bettilina.cinemanteca.data.model.Movie

@Database(entities = [Movie::class], exportSchema = false,  version = 4)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object{
        private val LOCK = Any()
        private const val DB_NAME = "movies"
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            synchronized(LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,
                        DB_NAME)
                            //TESTING PURPOSES ONLY
                        .allowMainThreadQueries()
                        // When DB version on device doesn't match schema version,
                        // recreate a new DB instead of crashing
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }
    }
}