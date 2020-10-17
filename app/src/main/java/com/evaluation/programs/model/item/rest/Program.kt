package com.evaluation.programs.model.item.rest

import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
data class Program(
    @SerializedName("id")
    val id: Int,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("channel_name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("start")
    val start: String?,
    @SerializedName("stop")
    val stop: String?,
    @SerializedName("now")
    val now: Int?
)