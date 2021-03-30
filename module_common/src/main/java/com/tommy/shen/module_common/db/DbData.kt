package com.tommy.shen.module_common.db

import androidx.room.Entity

@Entity(primaryKeys = ["data_key"])
data class DbData(var data_key: String, var data_value: String)
