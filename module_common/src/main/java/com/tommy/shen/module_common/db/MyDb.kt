package com.tommy.shen.module_common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tommy.shen.module_common.db.DbData
import com.tommy.shen.module_common.db.MyDao

/**
 * Created by Administrator on 2020/3/16.
 */
@Database(entities = [DbData::class], version = 1, exportSchema = false)
abstract class MyDb : RoomDatabase() {
    abstract fun myDao(): MyDao
}