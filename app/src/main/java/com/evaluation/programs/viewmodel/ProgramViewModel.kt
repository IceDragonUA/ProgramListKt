package com.evaluation.programs.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.evaluation.programs.interaction.AppProgramsInteraction
import com.evaluation.utils.empty

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
class ProgramViewModel @ViewModelInject constructor(
    private val interaction: AppProgramsInteraction
) : ViewModel() {

    private val query = MutableLiveData<String>()
    private val itemResult = map(query) {
        interaction.programList()
    }
    val items = switchMap(itemResult) { it.pagedList }
    val networkState = switchMap(itemResult) { it.networkState }

    init {
        this.query.value = empty()
    }
}