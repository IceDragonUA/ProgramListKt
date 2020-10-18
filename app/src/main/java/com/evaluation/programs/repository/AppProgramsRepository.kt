package com.evaluation.programs.repository

import android.content.Context
import com.evaluation.R
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.programs.adapter.viewholder.item.CardItemView
import com.evaluation.adapter.viewholder.item.NoItemView
import com.evaluation.programs.database.AppProgramsDatabaseDao
import com.evaluation.programs.mapper.ProgramMapper
import com.evaluation.programs.network.AppProgramsRestApiDao
import com.evaluation.utils.DIRECTION_DOWN
import com.evaluation.utils.defIfNull
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Vladyslav Havrylenko
 * @since 01.05.2020
 */
class AppProgramsRepository @Inject constructor(
    private val context: Context,
    private val mapper: ProgramMapper,
    private val appProgramsRestApiDao: AppProgramsRestApiDao,
    private val appProgramsDatabaseDao: AppProgramsDatabaseDao
) {

    fun programListInit(
        borderId: Int,
        direction: Int,
        onPrepared: () -> Unit,
        onSuccess: (MutableList<BaseItemView>) -> Unit,
        onError: (MutableList<BaseItemView>) -> Unit
    ): Disposable {
        return appProgramsRestApiDao.programList(borderId = borderId, direction = direction)
            .doOnSubscribe {
                onPrepared()
            }
            .subscribe(
                { programList ->
                    appProgramsDatabaseDao.deleteList()
                    appProgramsDatabaseDao.insertList(
                        programList.items.mapIndexed { index, program ->
                            mapper.toTableItem(program, programList.offset + index + 1)
                        }
                    )

                    val itemList: MutableList<BaseItemView> = mutableListOf()
                    val databaseProgramList = appProgramsDatabaseDao.programList()
                    databaseProgramList.forEach {
                        itemList.add(
                            CardItemView(
                                index = it.id.defIfNull().toString(),
                                beforeId = databaseProgramList.first().id,
                                afterId = databaseProgramList.last().id,
                                program = it
                            )
                        )
                    }
                    itemList.ifEmpty {
                        itemList.add(
                            NoItemView(
                                title = context.resources.getString(R.string.result).defIfNull()
                            )
                        )
                    }

                    onSuccess(itemList)
                },
                { errorMessage ->
                    Timber.e(errorMessage, "Loading error")

                    val itemList: MutableList<BaseItemView> = mutableListOf()
                    val databaseProgramList = appProgramsDatabaseDao.programList()
                    databaseProgramList.forEach {
                        itemList.add(
                            CardItemView(
                                index = it.id.defIfNull().toString(),
                                beforeId = databaseProgramList.first().id,
                                afterId = databaseProgramList.last().id,
                                program = it
                            )
                        )
                    }
                    itemList.ifEmpty {
                        itemList.add(
                            NoItemView(
                                title = context.resources.getString(R.string.result).defIfNull()
                            )
                        )
                    }

                    onError(itemList)
                }
            )
    }

    fun programListPaged(
        borderId: Int,
        direction: Int,
        onPrepared: () -> Unit,
        onSuccess: (MutableList<BaseItemView>) -> Unit,
        onError: () -> Unit
    ): Disposable {
        return appProgramsRestApiDao.programList(borderId = borderId, direction = direction)
            .doOnSubscribe {
                onPrepared()
            }
            .subscribe(
                { programList ->
                    appProgramsDatabaseDao.insertList(
                        when (direction) {
                            DIRECTION_DOWN -> {
                                programList.items.mapIndexed { index, program ->
                                    mapper.toTableItem(program, programList.offset + index + 1)
                                }
                            }
                            else -> {
                                programList.items.reversed().mapIndexed { index, program ->
                                    mapper.toTableItem(program, programList.offset + index + 1)
                                }
                            }
                        }
                    )

                    val itemList: MutableList<BaseItemView> = mutableListOf()
                    val databaseProgramList = appProgramsDatabaseDao.programPagedList(
                        programList.items_number,
                        when (direction) {
                            DIRECTION_DOWN -> appProgramsDatabaseDao.programCount() - programList.items_number
                            else -> 0
                        }
                    )

                    databaseProgramList.forEach {
                        itemList.add(
                            CardItemView(
                                index = it.id.defIfNull().toString(),
                                beforeId = if (programList.offset > 0) databaseProgramList.first().id else null,
                                afterId = if (programList.hasMore > 0) databaseProgramList.last().id else null,
                                program = it
                            )
                        )
                    }

                    onSuccess(itemList)
                },
                { errorMessage ->
                    Timber.e(errorMessage, "Loading error")
                    onError()
                }
            )
    }
}
