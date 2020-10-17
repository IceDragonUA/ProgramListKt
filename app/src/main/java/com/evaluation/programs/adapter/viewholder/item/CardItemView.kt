package com.evaluation.programs.adapter.viewholder.item

import com.evaluation.adapter.factory.TypesFactory
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.programs.model.item.database.ProgramTableItem

/**
 * @author Vladyslav Havrylenko
 * @since 03.10.2020
 */
data class CardItemView(
    override var index: String,
    override var beforeId: Int? = null,
    override var afterId: Int? = null,
    var program: ProgramTableItem
) : BaseItemView {

    override fun type(typesFactory: TypesFactory): Int = typesFactory.type(this)

}