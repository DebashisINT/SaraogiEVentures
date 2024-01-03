package com.ewentteamfsm.app.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface SourceMasterDao {

    @Insert
    fun insert(vararg obj: SourceMasterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertAll(kist: List<SourceMasterEntity>)

    @Query("select * from crm_source_master")
    fun getAll(): List<SourceMasterEntity>
}