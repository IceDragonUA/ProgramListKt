package com.evaluation.programs.adapter.viewholder

import android.view.View
import com.evaluation.R
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.viewholder.BaseViewHolder
import com.evaluation.programs.adapter.viewholder.item.CardItemView
import com.evaluation.utils.defIfNull
import com.evaluation.utils.initText
import com.evaluation.utils.loadFromUrl
import kotlinx.android.synthetic.main.program_card_item.view.*


class CardItemHolder(itemView: View, listener: AdapterItemClickListener<CardItemView>?) :
    BaseViewHolder<CardItemView>(itemView, listener) {

    override fun bind(item: CardItemView) {
        itemView.image.loadFromUrl(item.program.icon)
        itemView.title.initText(item.program.description)
        itemView.time.initText(
            item.program.start +
                    itemView.context.resources.getString(R.string.none).defIfNull() +
                    item.program.stop
        )
        itemView.channel.initText(item.program.name)
        itemView.now.visibility = if (item.program.now > 0) View.VISIBLE else View.GONE
        itemView.setOnClickListener {
            listener?.onClicked(item)
        }
    }

}