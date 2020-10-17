package com.evaluation.programs.dagger

import android.content.Context
import androidx.paging.PagedList
import com.evaluation.database.AppDatabase
import com.evaluation.executor.BaseExecutor
import com.evaluation.network.RestApi
import com.evaluation.programs.database.AppProgramsDatabaseDao
import com.evaluation.programs.datasource.AppProgramDataSource
import com.evaluation.programs.datasource.AppProgramDataSourceFactory
import com.evaluation.programs.interaction.AppProgramsInteraction
import com.evaluation.programs.interaction.AppProgramsInteractionImpl
import com.evaluation.programs.mapper.ProgramMapper
import com.evaluation.programs.network.AppProgramsRestApiDao
import com.evaluation.programs.network.AppProgramsRestApiDaoImpl
import com.evaluation.programs.repository.AppProgramsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.util.concurrent.Executor
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataProgramsModule {

    @Singleton
    @Provides
    fun appRest(appRest: RestApi, executor: BaseExecutor): AppProgramsRestApiDao = AppProgramsRestApiDaoImpl(appRest, executor)

    @Provides
    @Singleton
    fun appDao(appDatabase: AppDatabase): AppProgramsDatabaseDao = appDatabase.appProgramListDao()

    @Singleton
    @Provides
    fun appRepository(context: Context, mapper: ProgramMapper, remoteDao: AppProgramsRestApiDao, localDao: AppProgramsDatabaseDao) =
        AppProgramsRepository(context, mapper, remoteDao, localDao)

    @Singleton
    @Provides
    fun appDataSource(appRepository: AppProgramsRepository) = AppProgramDataSource(appRepository)

    @Singleton
    @Provides
    fun appDataSourceFactory(appDataSource: AppProgramDataSource) = AppProgramDataSourceFactory(appDataSource)

    @Singleton
    @Provides
    fun appInteraction(factory: AppProgramDataSourceFactory, config: PagedList.Config, networkExecutor: Executor): AppProgramsInteraction =
        AppProgramsInteractionImpl(factory, config, networkExecutor)

}