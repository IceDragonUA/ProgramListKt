package com.evaluation.programs.network

import android.annotation.SuppressLint
import android.os.Build
import com.evaluation.executor.ThreadExecutor
import com.evaluation.network.RestApi
import com.evaluation.programs.model.ProgramList
import io.reactivex.Single
import javax.inject.Inject

class AppProgramsRestApiDaoImpl @Inject constructor(
    private val appRest: RestApi,
    private val executor: ThreadExecutor
): AppProgramsRestApiDao {

    override fun programList(borderId: Int, direction: Int): Single<ProgramList> {
        return appRest.programList(serialNumber(), borderId, direction)
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    @SuppressLint("HardwareIds")
    override fun serialNumber(): String {
        val deviceId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    Build.getSerial()
                } catch (e: SecurityException) {
                    Build.SERIAL
                }
            } else Build.SERIAL
        return deviceId.toString()
    }

}