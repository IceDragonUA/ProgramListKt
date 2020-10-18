package com.evaluation.tests.dao.network

import com.evaluation.executor.TestExecutor
import com.evaluation.network.RestApi
import com.evaluation.programs.network.AppProgramsRestApiDao
import com.evaluation.programs.network.AppProgramsRestApiDaoImpl
import com.evaluation.tests.dao.RetrofitMocks
import com.evaluation.tests.test
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertNotNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


/**
 * @author Vladyslav Havrylenko
 * @since 11.10.2020
 */
class AppProgramsRestApiDaoTest {

    private lateinit var appProgramsRestApiDao: AppProgramsRestApiDao

    private var appRest: RestApi = RetrofitMocks.appRest

    @Mock
    private lateinit var executor: TestExecutor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        appProgramsRestApiDao = AppProgramsRestApiDaoImpl(appRest, executor)
        whenever(executor.mainExecutor).thenReturn(Schedulers.trampoline())
        whenever(executor.postExecutor).thenReturn(Schedulers.trampoline())
        whenever(appProgramsRestApiDao.serialNumber()).thenReturn("RZ8N31R7ENY")

    }

    @Test
    fun `should do call`() {
        assertNotNull(appRest)
        assertNotNull(executor)
        assertNotNull(appProgramsRestApiDao)

        appProgramsRestApiDao.programList(0, 0).test {
            assertValueCount(1)
            assertNoErrors()
            assertComplete()
        }
    }
}