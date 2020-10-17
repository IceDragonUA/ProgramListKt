package com.evaluation.programs.model

import com.evaluation.programs.model.item.rest.Program
import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
data class ProgramList(
    @SerializedName("items")
    val items: List<Program>,
    @SerializedName("items_number")
    val items_number: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("hasMore")
    val hasMore: Int
)