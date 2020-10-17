package com.evaluation.programs.adapter.viewholder

import android.view.View
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.viewholder.BaseViewHolder
import com.evaluation.programs.adapter.viewholder.item.CardItemView
import com.evaluation.utils.initText
import com.evaluation.utils.loadFromUrl
import kotlinx.android.synthetic.main.program_card_item.view.*


class CardItemHolder(itemView: View, listener: AdapterItemClickListener<CardItemView>?) : BaseViewHolder<CardItemView>(itemView, listener) {

    override fun bind(item: CardItemView) {
        itemView.image.loadFromUrl(item.program.icon)
        itemView.title.initText(item.program.name)
        itemView.time.initText(item.program.start)
        itemView.setOnClickListener {
            listener?.onClicked(item)
        }
    }

}