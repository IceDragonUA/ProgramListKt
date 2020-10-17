package com.evaluation.network

import com.evaluation.programs.model.ProgramList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("demo")
    fun programList(
        @Query("serial_number") serialNumber: String,
        @Query("borderId") borderId: Int,
        @Query("direction") direction: Int
    ): Single<ProgramList>

}