package com.evaluation.programs.network

import com.evaluation.programs.model.ProgramList
import io.reactivex.Single

interface AppProgramsRestApiDao {

    fun programList(borderId: Int, direction: Int): Single<ProgramList>

    fun serialNumber(): String

}