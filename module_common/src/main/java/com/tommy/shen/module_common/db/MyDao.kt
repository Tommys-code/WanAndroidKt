package com.tommy.shen.module_common.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dbData: DbData)

    fun save(data_key: String, data_value: String?) {
        insert(DbData(data_key, data_value ?: ""))
    }

    @Query("SELECT * FROM DbData WHERE data_key = :key")
    suspend fun load(key: String): DbData

    @Query("SELECT * FROM DbData")
    suspend fun loadAll(): List<DbData>

    @Query("DELETE FROM DbData WHERE data_key = :key")
    fun delete(key: String)

    @Query("DELETE FROM DbData WHERE data_key NOT IN (:keys)")
    fun deleteAllExcept(keys: Array<String>)

    @Query("SELECT * FROM DbData WHERE data_key = :key")
    suspend fun get(key: String): DbData?
}
