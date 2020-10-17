package com.evaluation.programs.mapper

import com.evaluation.programs.model.item.database.ProgramTableItem
import com.evaluation.programs.model.item.rest.Program
import com.evaluation.utils.defIfNull
import com.evaluation.utils.toDate
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 01.10.2020
 */
class ProgramMapper @Inject constructor() {

    fun toTableItem(item: Program, index: Int): ProgramTableItem {
        return item.let {
            ProgramTableItem(
                index = index,
                id = it.id,
                icon = it.icon.defIfNull(),
                name = it.name.defIfNull(),
                description = it.description.defIfNull(),
                start = it.start.defIfNull().toDate(),
                stop = it.stop.defIfNull().toDate(),
                now = it.now.defIfNull()
            )
        }
    }
}