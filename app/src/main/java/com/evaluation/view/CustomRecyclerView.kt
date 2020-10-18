package com.evaluation.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evaluation.R
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.CustomListAdapter
import com.evaluation.adapter.decorator.DividerItemDecorator
import com.evaluation.adapter.factory.TypesFactoryImpl
import com.evaluation.adapter.viewholder.item.BaseItemView

/**
 * @author Vladyslav Havrylenko
 * @since 03.10.2020
 */
class CustomRecyclerView : RecyclerView, AdapterItemClickListener<BaseItemView> {

    lateinit var listener: AdapterItemClickListener<BaseItemView>

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        layoutManager = LinearLayoutManager(context)
        adapter = CustomListAdapter(TypesFactoryImpl(), this)
        itemAnimator = null
        addItemDecoration(DividerItemDecorator(ContextCompat.getDrawable(context, R.drawable.ic_divider)))
    }

    override fun getAdapter(): CustomListAdapter =
        super.getAdapter() as CustomListAdapter

    override fun onClicked(item: BaseItemView) {
        listener.onClicked(item)
    }
}