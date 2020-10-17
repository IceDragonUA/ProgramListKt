package com.evaluation.programs.interaction

import androidx.annotation.MainThread
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.programs.datasource.AppProgramDataSourceFactory
import com.evaluation.utils.Listing
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
class AppProgramsInteractionImpl @Inject constructor(
    private val factory: AppProgramDataSourceFactory,
    private val config: PagedList.Config,
    private val networkExecutor: Executor
) : AppProgramsInteraction {

    @MainThread
    override fun programList(): Listing<BaseItemView> {

        val liveList =
            LivePagedListBuilder(factory, config)
                .setFetchExecutor(networkExecutor)
                .build()

        return Listing(
            pagedList = liveList,
            networkState = factory.network
        )
    }
}
