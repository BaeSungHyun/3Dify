package com.part2.a3dify.app_containers

import com.part2.a3dify.app_entry.repository.RegisterRepository
import com.part2.a3dify.app_entry.viewmodels.RegisterFragmentViewModel

class RegisterFragmentContainer {
    val registerFragmentModelFactory = RegisterFragmentViewModel.factory
    val registerRepository = RegisterRepository()
}