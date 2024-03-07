package com.part2.a3dify.app_entry.uistates

import com.part2.a3dify.app_entry.image_recycler_view.ImageDataClass

data class MainFragmentUiStates (
    val uriList : List<ImageDataClass>? = null,
    val showLoading : Boolean? = null
)