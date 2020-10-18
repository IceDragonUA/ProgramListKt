package com.evaluation.programs.network

import com.evaluation.executor.ThreadExecutor
import com.evaluation.network.RestApi
import com.evaluation.programs.model.ProgramList
import com.evaluation.serial.SerialProvider
import io.reactivex.Single
import javax.inject.Inject

class AppProgramsRestApiDaoImpl @Inject constructor(
    private val appRest: RestApi,
    private val appSerialProvider: SerialProvider,
    private val executor: ThreadExecutor
): AppProgramsRestApiDao {

    override fun programList(borderId: Int, direction: Int): Single<ProgramList> {
        return appRest.programList(appSerialProvider.serial, borderId, direction)
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

}