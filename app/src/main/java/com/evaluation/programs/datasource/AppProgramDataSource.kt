package com.evaluation.programs.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.adapter.viewholder.item.NoItemView
import com.evaluation.programs.repository.AppProgramsRepository
import com.evaluation.utils.*
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 08.10.2020
 */
class AppProgramDataSource @Inject constructor(
    private val repository: AppProgramsRepository
) : PageKeyedDataSource<Int, BaseItemView>() {

    val network = MutableLiveData<Boolean>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, BaseItemView>) {
        repository.programListInit(
            borderId = DEFAULT_BORDER_ID,
            direction = DEFAULT_DIRECTION,
            onPrepared = {
                postInitialState(NetworkState.LOADING)
            },
            onSuccess = { programList ->
                val refresh = programList.firstOrNull() is NoItemView
                postInitialState(refreshNetworkState(refresh))
                callback.onResult(programList, programList.first().beforeId, programList.last().afterId)
            },
            onError = { programList ->
                postInitialState(NetworkState.LOADED)
                callback.onResult(programList, null, null)
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BaseItemView>) {
        repository.programListPaged(
            borderId = params.key,
            direction = DIRECTION_UP,
            onPrepared = {
                postBeforeAfterState(NetworkState.LOADING)
            },
            onSuccess = { programList ->
                postBeforeAfterState(NetworkState.LOADED)
                callback.onResult(programList, programList.first().beforeId)
            },
            onError = {
                postBeforeAfterState(NetworkState.LOADED)
                callback.onResult(listOf(), null)
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BaseItemView>) {
        repository.programListPaged(
            borderId = params.key,
            direction = DIRECTION_DOWN,
            onPrepared = {
                postBeforeAfterState(NetworkState.LOADING)
            },
            onSuccess = { programList ->
                postBeforeAfterState(NetworkState.LOADED)
                callback.onResult(programList, programList.last().afterId)
            },
            onError = {
                postBeforeAfterState(NetworkState.LOADED)
                callback.onResult(listOf(), null)
            })
    }

    private fun postInitialState(networkState: NetworkState) {
        network.postValue(networkState.value())
    }

    private fun postBeforeAfterState(networkState: NetworkState) {
        network.postValue(networkState.value())
    }

    private fun refreshNetworkState(refresh: Boolean) =
        if (refresh) NetworkState.LOADED else NetworkState.LOADING

}
