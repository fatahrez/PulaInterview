package com.fatahapps.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fatahapps.data.local.model.EngStringsLocal

@Dao
interface EngStringsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEngStrings(engStringsLocal: EngStringsLocal)

    @Query("DELETE from engstringslocal")
    suspend fun deleteEngStrings()

    @Query("SELECT * FROM engstringslocal")
    suspend fun getEngStrings(): EngStringsLocal
}