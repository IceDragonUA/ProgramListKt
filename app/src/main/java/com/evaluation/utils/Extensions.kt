package com.evaluation.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import com.evaluation.R
import com.evaluation.glide.GlideApp
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Vladyslav Havrylenko
 * @since 01.10.2020
 */
fun String?.defIfNull() = this ?: ""
fun Int?.defIfNull(def: Int = 0) = this ?: def

fun empty() = ""

fun ImageView.loadFromUrl(url: String) {
    GlideApp.with(this.context.applicationContext)
        .load(url)
        .into(this)
}

fun TextView.initText(text: String) {
    if (text.isNotEmpty()) this.text = text else this.text =
        this.context.applicationContext.getString(R.string.none)
}

@SuppressLint("SimpleDateFormat")
fun String.toDate(): String {
    var date = empty()
    val hhmm = SimpleDateFormat("HH:mm")
    val yyyymmddhhmm = SimpleDateFormat("yyyy-MM-dd HH:mm")
    try {
        date = hhmm.format(yyyymmddhhmm.parse(this))
    } catch (ex: ParseException) {
        Timber.e(ex.message, "Parse exception")
    }
    return date
}