package com.evaluation.adapter.factory

import android.view.View
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.viewholder.BaseViewHolder
import com.evaluation.programs.adapter.viewholder.item.CardItemView
import com.evaluation.adapter.viewholder.item.NoItemView

interface TypesFactory {

    fun type(item: NoItemView): Int

    fun type(item: CardItemView): Int

    fun holder(type: Int, view: View, listener: AdapterItemClickListener<*>?): BaseViewHolder<*>

}