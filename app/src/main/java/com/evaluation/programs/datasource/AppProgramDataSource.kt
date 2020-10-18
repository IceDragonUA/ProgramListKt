package com.evaluation.programs.datasource

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.adapter.viewholder.item.NoItemView
import com.evaluation.programs.repository.AppProgramsRepository
import com.evaluation.utils.*
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Vladyslav Havrylenko
 * @since 08.10.2020
 */
class AppProgramDataSource @Inject constructor(
    private val context: Context,
    private val repository: AppProgramsRepository
) : PageKeyedDataSource<Int, BaseItemView>() {

    val network = MutableLiveData<Boolean>()

    private var disposable: Disposable? = null
    private val compositeDisposable = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, BaseItemView>
    ) {
        repository.programListInit(
            borderId = DEFAULT_BORDER_ID,
            direction = DEFAULT_DIRECTION,
            onPrepared = {
                postInitialState(NetworkState.LOADING)
            },
            onSuccess = { programList ->
                val refresh = programList.firstOrNull() is NoItemView
                postInitialState(refreshNetworkState(refresh))
                callback.onResult(
                    programList,
                    programList.first().beforeId,
                    programList.last().afterId
                )
            },
            onError = {
                checkNetworkConnectionForInit(DEFAULT_BORDER_ID, DEFAULT_DIRECTION, callback)
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
                checkNetworkConnectionForPage(DIRECTION_UP, params, callback)
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
                checkNetworkConnectionForPage(DIRECTION_DOWN, params, callback)
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

    private fun checkNetworkConnectionForInit(
        id: Int,
        direction: Int,
        callback: LoadInitialCallback<Int, BaseItemView>
    ) {
        compositeDisposable.add(
            ReactiveNetwork
                .observeNetworkConnectivity(context)
                .flatMapSingle { ReactiveNetwork.checkInternetConnectivity() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it) {
                            repository.programListInit(
                                borderId = id,
                                direction = direction,
                                onPrepared = {
                                    postInitialState(NetworkState.LOADING)
                                },
                                onSuccess = { programList ->
                                    val refresh = programList.firstOrNull() is NoItemView
                                    postInitialState(refreshNetworkState(refresh))
                                    callback.onResult(
                                        programList,
                                        programList.first().beforeId,
                                        programList.last().afterId
                                    )
                                    disposable?.dispose()
                                },
                                onError = { programList ->
                                    postInitialState(NetworkState.LOADED)
                                    callback.onResult(
                                        programList,
                                        programList.first().beforeId,
                                        programList.last().afterId
                                    )
                                    disposable?.dispose()
                                })
                        }
                    },
                    { Timber.e(it.message, "Loading error") },
                    { Timber.d("Loading completed") },
                    { disposable = it }
                ))
    }

    private fun checkNetworkConnectionForPage(
        direction: Int,
        params: LoadParams<Int>,
        callback: LoadCallback<Int, BaseItemView>
    ) {
        compositeDisposable.add(
            ReactiveNetwork
                .observeNetworkConnectivity(context)
                .flatMapSingle { ReactiveNetwork.checkInternetConnectivity() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it) {
                            repository.programListPaged(
                                borderId = params.key,
                                direction = direction,
                                onPrepared = {
                                    postBeforeAfterState(NetworkState.LOADING)
                                },
                                onSuccess = { programList ->
                                    postBeforeAfterState(NetworkState.LOADED)
                                    callback.onResult(
                                        programList,
                                        programBorderIdByDirection(direction, programList)
                                    )
                                    disposable?.dispose()
                                },
                                onError = {
                                    postBeforeAfterState(NetworkState.LOADED)
                                    callback.onResult(listOf(), null)
                                    disposable?.dispose()
                                })
                        }
                    },
                    { Timber.e(it.message, "Loading error") },
                    { Timber.d("Loading completed") },
                    { disposable = it }
                ))
    }

    private fun programBorderIdByDirection(
        direction: Int,
        programList: MutableList<BaseItemView>
    ): Int? =
        when (direction) {
            DIRECTION_DOWN -> programList.last().afterId
            else -> programList.first().beforeId
        }

}
