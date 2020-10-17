package com.evaluation.programs.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.evaluation.programs.model.item.database.ProgramTableItem

@Dao
interface AppProgramsDatabaseDao {

    @Query("SELECT * FROM programs ORDER BY `index` ASC")
    fun programList(): List<ProgramTableItem>

    @Query("SELECT * FROM programs ORDER BY `index` ASC LIMIT :limit OFFSET :offset ")
    fun programPagedList(limit: Int, offset: Int): List<ProgramTableItem>

    @Query("SELECT COUNT(`index`) FROM programs")
    fun programCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(programs: List<ProgramTableItem>)

    @Query("DELETE FROM programs")
    fun deleteList()

}