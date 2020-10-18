package com.evaluation.programs.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.programs.interaction.AppProgramsInteraction
import com.evaluation.utils.Listing
import com.evaluation.utils.empty

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
class ProgramViewModel @ViewModelInject constructor(
    interaction: AppProgramsInteraction
) : ViewModel() {

    private val itemResult = MutableLiveData(interaction.programList())
    val items = switchMap(itemResult) { it.pagedList }
    val networkState = switchMap(itemResult) { it.networkState }

}