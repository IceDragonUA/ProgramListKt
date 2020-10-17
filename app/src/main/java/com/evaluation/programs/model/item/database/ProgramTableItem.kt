package com.evaluation.programs.model.item.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Entity(tableName = "programs", indices = [Index(value = ["id"], unique = true)])
data class ProgramTableItem(
    @PrimaryKey
    var index: Int,
    @ColumnInfo(name = "id")
    val id: Int,
    val icon: String,
    val name: String,
    val description: String,
    val start: String,
    val stop: String,
    val now: Int,
)