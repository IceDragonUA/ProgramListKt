package com.evaluation.programs.datasource

import androidx.paging.DataSource
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.utils.empty
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 08.10.2020
 */

class AppProgramDataSourceFactory @Inject constructor(private var dataSource: AppProgramDataSource) :
    DataSource.Factory<Int, BaseItemView>() {

    var network = dataSource.network

    override fun create(): DataSource<Int, BaseItemView> {
        return dataSource
    }

}