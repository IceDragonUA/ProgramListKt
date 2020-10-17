package com.evaluation.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.evaluation.programs.database.AppProgramsDatabaseDao
import com.evaluation.programs.model.item.database.ProgramTableItem
import com.evaluation.utils.DATABASE_VERSION

@Database(entities = [ProgramTableItem::class], version = DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appProgramListDao(): AppProgramsDatabaseDao

}






