package com.dicoding.mycatapplication.core.data.local.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dicoding.mycatapplication.R
import com.dicoding.mycatapplication.core.domain.BreedEntity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.Executors

@Database(version = 1, entities = [BreedEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun breedDao(): BreedDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cat.db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val executor = Executors.newSingleThreadExecutor()
                        executor.execute {
                            val dao = getInstance(context).breedDao()
                            fillWithStartingData(context, dao)
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }

        private fun fillWithStartingData(context: Context, dao: BreedDao) {
            val breed = loadJsonArray(context)
            try {
                if (breed != null) {
                    for (i in 0 until breed.length()) {
                        val item = breed.getJSONObject(i)
                        dao.insertBreed(
                            BreedEntity(
                                breedName = item.getString("breed"),
                                country = item.getString("country"),
                                origin = item.getString("origin"),
                                pattern = item.getString("pattern"),
                            )
                        )
                    }
                }
            } catch (e: JSONException) {
                Log.d(TAG, "cause: ${e.message.toString()}")
            }
        }

        private fun loadJsonArray(context: Context): JSONArray? {
            val builder = StringBuilder()
            val `in` = context.resources.openRawResource(R.raw.breeds)
            val reader = BufferedReader(InputStreamReader(`in`))
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
                val json = JSONObject(builder.toString())
                return json.getJSONArray("breeds")
            } catch (e: IOException) {
                Log.d(TAG, "cause: ${e.message.toString()}")
            } catch (e: JSONException) {
                Log.d(TAG, "cause: ${e.message.toString()}")
            }
            return null
        }

        private const val TAG = "cek Database"

    }

}