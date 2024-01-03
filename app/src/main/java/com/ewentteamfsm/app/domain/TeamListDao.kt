package com.ewentteamfsm.app.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ewentteamfsm.app.AppConstant

@Dao
interface TeamListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertAll(kist: List<TeamListEntity>)

    @Query("select * from "+AppConstant.TEAM_LIST)
    fun getAll(): List<TeamListEntity>

}