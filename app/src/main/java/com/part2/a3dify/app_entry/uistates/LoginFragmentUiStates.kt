package com.part2.a3dify.app_entry.uistates

data class LoginFragmentUiStates (
    val isLogin: Boolean = false,  // below values are non-null only when 'true'
    val userId: String? = null,
    val name: String? = null
)