package com.evaluation.adapter.viewholder.item

import com.evaluation.adapter.factory.TypesFactory

interface BaseItemView {

    var index: String

    var beforeId: Int?

    var afterId: Int?

    fun type(typesFactory: TypesFactory): Int

}