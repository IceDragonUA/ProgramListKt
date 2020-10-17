package com.evaluation.adapter.viewholder

import android.view.View
import com.evaluation.adapter.viewholder.item.NoItemView
import kotlinx.android.synthetic.main.program_no_item.view.*

class NoItemHolder(itemView: View) : BaseViewHolder<NoItemView>(itemView, null) {

    override fun bind(item: NoItemView) {
        itemView.result.text = item.title
    }

}